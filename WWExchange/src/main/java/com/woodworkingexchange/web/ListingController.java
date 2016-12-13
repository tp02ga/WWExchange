package com.woodworkingexchange.web;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.woodworkingexchange.command.ContactForm;
import com.woodworkingexchange.command.MyListingsCommand;
import com.woodworkingexchange.command.SearchCriteria;
import com.woodworkingexchange.domain.FeedbackSurvey;
import com.woodworkingexchange.domain.Listing;
import com.woodworkingexchange.domain.Users;
import com.woodworkingexchange.repositories.FeedbackSurveyRepository;
import com.woodworkingexchange.repositories.ListingRepository;
import com.woodworkingexchange.repositories.UserRepository;
import com.woodworkingexchange.service.MailService;
import com.woodworkingexchange.service.PropertiesService;

@RequestMapping("/listings")
@Controller
public class ListingController 
{
  Logger log = LoggerFactory.getLogger(getClass());
  
  @Autowired
  private ListingRepository listingDao;
  @Autowired
  private FeedbackSurveyRepository feedbackSurveyDao;
  @Autowired
  private UserRepository userDao;
  @Autowired
  private MailService mailService;
  
  private static final String US_EAST_BUCKET_NAME = "elasticbeanstalk-us-east-1-213751294289";
  private AmazonS3Client amazonS3Client;
  
  Properties props = new Properties();
  
  public ListingController ()
  {
    props = PropertiesService.getWoodworkingProperties();
  }
  
  @RequestMapping(value="viewListing", method=RequestMethod.GET)
  public String handleViewListing(@AuthenticationPrincipal Users user, @RequestParam(value="id",required=true) Long listingId, ModelMap model)
  {
    Listing listing = listingDao.findOne(listingId);
    model.put("listing", listing);
    model.put("contactForm", new ContactForm());
    model.put("usersEmail", user == null ? "" : user.getEmail());
    return "listings/viewListing";
  }
  
//  @RequestMapping(value="viewListing", method=RequestMethod.POST)
//  public String handleListingPost(@ModelAttribute("contactForm") ContactForm form, ModelMap model)
//  {
//    model.put("messageSent", true);
//    return "listings/viewListing";
//  }
  
  @RequestMapping(value="sendMessage", method=RequestMethod.POST)
  public @ResponseBody ModelMap handleListingPost(@RequestParam(value="email") String email, 
      @RequestParam(value="message") String message, 
      @RequestParam(value="fromEmail") String fromEmail,
      @RequestParam(value="product") String product,
      HttpServletRequest request,
      ModelMap model) throws Exception
  {
    Users user = userDao.findUsersByEmail(fromEmail);
    mailService.sendMessage(email, message, user.getEmail(), user.getName(), product);
    model.put("messageSent", true);
    return model;
  }
  
  @RequestMapping(value="postListing", method=RequestMethod.POST)
  public @ResponseBody ModelMap postListing(@RequestParam(value="listingId") Long listingId,
      HttpServletRequest request,
      ModelMap model) throws Exception
  {
    Listing listingById = listingDao.findOne(listingId);
    listingDao.save(listingById);
    return model;
  }
  
  @RequestMapping(value="/myListings", method=RequestMethod.GET)
  public String handleMyListings(@AuthenticationPrincipal Users user, ModelMap model, HttpServletRequest request, Principal principal) throws Exception
  {
    user = userDao.findUserWithAddress(user.getEmail());
    List<Listing> listings = listingDao.findByUsers(user);
    MyListingsCommand command = new MyListingsCommand(listings, null, new FeedbackSurvey());
    
    model.put("MyListingsCommand", command);
    model.put("user", user);
    return "listings/myListings";
  }
  
  @RequestMapping(value="/renewListing", method=RequestMethod.GET)
  public String renewListing(@AuthenticationPrincipal Users user, @RequestParam("id") Long id, 
      ModelMap model, HttpServletRequest request, Principal principal) throws Exception
  {
    listingDao.renewListingById(id);
    
    user = userDao.findUserWithAddress(user.getEmail());
    List<Listing> listings = listingDao.findByUsers(user);
    MyListingsCommand command = new MyListingsCommand(listings, null, new FeedbackSurvey());
    
    model.put("MyListingsCommand", command);
    model.put("user", user);
    
    return "listings/myListings";
  }
  
  @RequestMapping(value="/myListings", method=RequestMethod.POST)
  public String myListingsPost(@AuthenticationPrincipal Users user, @ModelAttribute("MyListingsCommand") MyListingsCommand listingCommand, 
      ModelMap model, HttpServletRequest request, Principal principal) throws Exception
  {
    user = userDao.findUserWithAddress(user.getEmail());
    String action = listingCommand.getAction();
    if (action != null && action.indexOf(":") > 0)
    {
      String[] actionValue = action.split(":");
      if ("del".equalsIgnoreCase(actionValue[0]))
      {
        Long idToDelete = Long.parseLong(actionValue[1]);
        Listing listingToDelete = listingDao.findOne(idToDelete);
        listingDao.delete(listingToDelete);
        feedbackSurveyDao.save(listingCommand.getFeedbackSurvey());
        ListIterator<Listing> listItr = listingCommand.getListings().listIterator();
        while (listItr.hasNext())
        {
          Listing aListing = listItr.next();
          if (aListing.getId().equals(idToDelete))
          {
            listItr.remove();
            break;
          }
        }
      }
      else if ("delAccount".equalsIgnoreCase(actionValue[0]))
      {
        if (actionValue.length > 1)
        {
          String passwordConfirm = actionValue[1];
          if (user.getPassword().equals(passwordConfirm))
          {
            userDao.delete(user);
            return "redirect:/j_spring_security_logout";
          }
        }
        model.put("MyListingsCommand", listingCommand);
        model.put("error", "Password does not match the one in your profile");
        return "listings/myListings";
      }
    }
    if (listingCommand.getListings().size() > 0)
    {
      listingCommand.setListings(populateListingsCommand(listingCommand.getListings()));
      model.put("MyListingsCommand", listingCommand);
    }
    else
    {
      model.put("MyListingsCommand", new MyListingsCommand());
    }
    model.put("user", user);
    
    return "listings/myListings";
  }
  
  private List<Listing> populateListingsCommand(List<Listing> incompleteListings)
  {
    List<Listing> listings = new ArrayList<Listing>();
    listings = listingDao.findByListingsIn(incompleteListings);
    return listings; 
  }
  
  @RequestMapping(value="/listing")
  public String handleListings(@AuthenticationPrincipal Users user, @ModelAttribute("SearchCriteria") SearchCriteria searchCriteria, 
      @RequestParam(value="keyword",required=false) String keyword, 
      @RequestParam(value="page",required=false) Integer page, 
      ModelMap model, HttpServletRequest request) throws Exception
  {
    if (user != null)
      user = userDao.findUserWithAddress(user.getEmail());
    List<Listing> listings;
    sanitizeSearchCriteria(searchCriteria);
    if (page == null)
      page = 0;
    if (keyword != null && !keyword.isEmpty())
    {
      Page<Listing> listingsByKeyword = listingDao.getListingsByKeyword(keyword, new PageRequest(page, 10));
      listings = listingsByKeyword.getContent();
      
      double count = listingsByKeyword.getSize();
      model.put("count", (((int)count) == 0 ? 1 : ((int)count)));
      model.put("keyword",  keyword);
    }
    else
    {
      Page<Listing> listingsByUser = listingDao.getListingsByUser(/*searchCriteria, */user, new PageRequest(page, 10));
      listings = listingsByUser.getContent();
      model.put("count", listingsByUser.getSize());
    }
    model.put("listings", listings);
    model.put("page", page);
    if (user != null)
    {
      model.put("user", user);
    }
    populateModel(model);
    return "listings/listing";
  }
  
  void sanitizeSearchCriteria(SearchCriteria searchCriteria)
  {
    String[] condition = searchCriteria.getCondition();
    String manufacturer = searchCriteria.getManufacturer();
    String region = searchCriteria.getRegion();
    String machineType = searchCriteria.getMachineType();
    
    if (condition == null || condition.length == 0)
    {
      setAllConditionsForSearchCriteria(searchCriteria);
    }
    if (manufacturer != null && (manufacturer.equals("All Brands or Manufacturers") || manufacturer.contains("-----")))
    {
      searchCriteria.setManufacturer(null);
    }
    if (machineType != null && (machineType.equals("All Machine Types") || manufacturer.contains("-----")))
    {
      searchCriteria.setMachineType(null);
    }
    if (region != null && (region.equals("All Regions") || region.contains("-----")))
    {
      searchCriteria.setRegion(null);
    }
  }

  void setAllConditionsForSearchCriteria(SearchCriteria searchCriteria)
  {
    Condition[] values = Condition.values();
    String[] conditionStrings = new String[values.length];
    for (int i=0; i<values.length; i++)
    {
      conditionStrings[i] = values[i].getConditionString();
    }
    searchCriteria.setCondition(conditionStrings);
  }

  @SuppressWarnings("unchecked")
  @RequestMapping(value="/add_listing", method=RequestMethod.GET)
  public String handleAddListing(@AuthenticationPrincipal Users user, ModelMap model, HttpServletRequest request, Principal principal) throws Exception
  {
    user = userDao.findUserWithAddress(user.getEmail());
    String listingId = request.getParameter("id");
    model.put("listing", new Listing());
    model.put("user", user);
    populateModel(model);
    ((List<String>)model.get("conditions")).add(0, "");
    if (listingId != null)
    {
      if (user != null) 
      {
        Listing listing = listingDao.findOne(Long.parseLong(listingId));
        if (listing == null || user.getId() != listing.getUserId())
        {
          throw new Exception("You don't have the security rights to view this page.");
        }
        model.put("listing", listing);
      }
    }
    
    return "listings/addListing";
  }

  @ExceptionHandler(Exception.class)
  public ModelAndView handleAllExceptions(Exception e,
      HttpServletRequest request)
  {
    ModelAndView model = new ModelAndView("error");
    model.addObject("error", e);
    log.error("Error in ListingController", e);
    return model;
  }
  
  @SuppressWarnings("unchecked")
  @RequestMapping(value="/add_listing", method=RequestMethod.POST)
  public String handlePostAddListing(@AuthenticationPrincipal Users user, @ModelAttribute("listing") Listing listing,
      @RequestParam(value="file1", required=false) MultipartFile uploadedFile1,
      @RequestParam(value="file2", required=false) MultipartFile uploadedFile2,
      @RequestParam(value="file3", required=false) MultipartFile uploadedFile3,
      @RequestParam(value="file4", required=false) MultipartFile uploadedFile4,
      ModelMap model, HttpServletRequest request, Principal principal) throws Exception
  {
    user = userDao.findUserWithAddress(user.getEmail());
    
    listing.setUsers(user);
    
    Listing savedListing = listingDao.save(listing);
    Long listingId = savedListing.getId();
    
    log.debug("Saving images to S3");
    Map<Integer, String> savedImages = saveImages(listingId, uploadedFile1, uploadedFile2, uploadedFile3, uploadedFile4);
    Class<Listing> listingClass = (Class<Listing>) Class.forName("com.woodworkingexchange.domain.Listing");
    Method[] methods = listingClass.getDeclaredMethods();
    for(Integer key : savedImages.keySet())
    {
      for (Method method : methods)
      {
        if (method.getName().startsWith("setImage"+(key+1)))
        {
          method.invoke(listing, savedImages.get(key));
        }
      }
    }
    listingDao.save(listing);
    return "redirect:/listings/viewListing?id="+listingId;
  }
  
  private URL uploadTempFileToS3(MultipartFile img, String name) throws IOException
  {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(img.getSize());
    metadata.setContentType(img.getContentType());
    
    PutObjectRequest req = new PutObjectRequest(US_EAST_BUCKET_NAME, "images/" + name, img.getInputStream(), metadata).withCannedAcl(CannedAccessControlList.PublicRead);
    
    amazonS3Client.putObject(req);
    
    return amazonS3Client.getUrl(US_EAST_BUCKET_NAME, "images/" + name);
  }
  
  private Map<Integer, String> saveImages(Long listingId, MultipartFile... files) throws IOException
  {
    Map<Integer, String> fileNames = new HashMap<Integer, String>(files.length);
    for (int i=0; i<files.length; i++)
    {
      if (files[i] != null && files[i].getSize() > 0)
      {
        String randomChars = String.format("%s", RandomStringUtils.randomAlphanumeric(15));
        URL url = uploadTempFileToS3(files[i], randomChars + "_" + files[i].getOriginalFilename());
        fileNames.put(i, url.toString());
//        FileOutputStream fos = null;
//        try
//        {
//          String ext = getExtension(files[i].getName());
//          log.debug("File extention is: " + ext);
//          File storedFile = new File(props.getProperty("images.dir") + listingId + "_" + i + "." + ext);
//          log.debug("storedFile is: " + storedFile.getAbsolutePath());
//          fos = new FileOutputStream(storedFile);
//          fos.write(files[i].getBytes());
//          fileNames.put(i, storedFile.getPath());
//          BufferedImage img = null;
//          BufferedImage scaledImg = null;
//          try
//          {
//            img = ImageIO.read(storedFile); // load image
//            scaledImg = Scalr.resize(img, 200);
//            ImageIO.write(scaledImg, ext, new File(props.getProperty("thumbs.dir") + listingId + "_" + i + "." + ext));
//          }
//          finally
//          {
//            if (img != null)
//              img.flush();
//            if (scaledImg != null)
//              scaledImg.flush();
//          }
//        } 
//        catch (FileNotFoundException e)
//        {
//          e.printStackTrace();
//          throw new FileNotFoundException("Your server isn't configured to read/write files correctly."); 
//        }
      }
    }
    return fileNames;
  }

  void populateModel(ModelMap model)
  {
    List<String> conditions = new ArrayList<String>();
    conditions.add("Excellent");
    conditions.add("Very Good");
    conditions.add("Good");
    conditions.add("Fair");
    conditions.add("As Is");
    model.put("conditions", conditions);
    
    List<String> methodOfContact = new ArrayList<String>();
    methodOfContact.add("e-mail");
    methodOfContact.add("phone");
    model.put("contactMethod", methodOfContact);
    
    List<String> machineTypes = new ArrayList<String>();
    machineTypes.add("Abrasive Planers"); machineTypes.add("Air Compressors"); machineTypes.add("Boring Equipment"); machineTypes.add("Carving Equipment"); machineTypes.add("Case and Frame Clamps");
    machineTypes.add("Clamp Carriers"); machineTypes.add("Clamps"); machineTypes.add("CNC Machinery"); machineTypes.add("Combination Machines"); machineTypes.add("Countertop / Postforming Equip.");
    machineTypes.add("Door & Stair Mfg Equip."); machineTypes.add("Dovetail & Drawer Equip."); machineTypes.add("Dust Collectors"); machineTypes.add("Edgebanders"); machineTypes.add("Finger Jointers");
    machineTypes.add("Finish Equipment"); machineTypes.add("Glue & Dowel Inserters"); machineTypes.add("Glueing Equipment"); machineTypes.add("Hammermills and Chippers"); machineTypes.add("Jointers");
    machineTypes.add("Laminating Equipment"); machineTypes.add("Laser Cutting Equipment"); machineTypes.add("Lathes"); machineTypes.add("Louver and Shutter Equipment"); machineTypes.add("Material Handling Equipment");
    machineTypes.add("Mortisers"); machineTypes.add("Moulders"); machineTypes.add("Panel Saws"); machineTypes.add("Pelleting Equipment"); machineTypes.add("Planers");
    machineTypes.add("Presses"); machineTypes.add("Rip Saws and Optimizers"); machineTypes.add("Routers - CNC & Manual"); machineTypes.add("Sanders - Finishing"); machineTypes.add("Sanders - Single Head");
    machineTypes.add("Sanders - Multi Head"); machineTypes.add("Sanders - Profile"); machineTypes.add("Sanders - Spindle/Disc/Misc"); machineTypes.add("Sanders - Stroke"); machineTypes.add("Saw Mill Equipment");
    machineTypes.add("Saws - Sliding Table"); machineTypes.add("Saws - Vertical"); machineTypes.add("Saws - Rip / Gang"); machineTypes.add("Saws - Rip / Straight"); machineTypes.add("Saws - Band / Scroll");
    machineTypes.add("Saws - Cut Off / Miter"); machineTypes.add("Saws - Optimizer"); machineTypes.add("Saws - Radial Arm"); machineTypes.add("Saws - Table"); machineTypes.add("Saws - Misc");
    machineTypes.add("Shapers"); machineTypes.add("Tenoners"); machineTypes.add("Vacuum Pumps"); machineTypes.add("Veneer Equipment"); machineTypes.add("Window Equipment");
    model.put("machineTypes", machineTypes);
    
    List<String> brands = new ArrayList<String>();
    brands.add("All Brands or Manufacturers");brands.add("-----------");
    brands.addAll(listingDao.getBrands());
    model.put("brands", brands);
    
    List<String> regions = new ArrayList<String>();
    regions.add("All Regions"); regions.add("----------");
    regions.add("United States");regions.add("----------");
    regions.add("Alabama");regions.add("Alaska");regions.add("Arizona");regions.add("Arkansas");regions.add("California");regions.add("Colorado");
    regions.add("Connecticut");regions.add("Delaware");regions.add("Florida");regions.add("Georgia");regions.add("Hawaii");regions.add("Idaho");
    regions.add("Illinois");regions.add("Indiana");regions.add("Iowa");regions.add("Kansas");regions.add("Kentucky");regions.add("Louisiana");
    regions.add("Maine");regions.add("Maryland");regions.add("Massachusetts");regions.add("Michigan");regions.add("Minnesota");regions.add("Mississippi");
    regions.add("Missouri");regions.add("Montana");regions.add("Nebraska");regions.add("Nevada");regions.add("New Hampshire");regions.add("New Jersey");
    regions.add("New Mexico");regions.add("New York");regions.add("North Carolina");regions.add("North Dakota");regions.add("Ohio");regions.add("Oklahoma");
    regions.add("Oregon");regions.add("Pennsylvania");regions.add("Rhode Island");regions.add("South Carolina");regions.add("South Dakota");regions.add("Tennessee");
    regions.add("Texas");regions.add("Utah");regions.add("Vermont");regions.add("Virginia");regions.add("Washington");regions.add("West Virginia");
    regions.add("Wisconsin");regions.add("Wyoming");
    regions.add("----------");regions.add("Canada");regions.add("----------");
    regions.add("Alberta");
    regions.add("British Columbia");regions.add("Manitoba");regions.add("New Brunswick");regions.add("Newfoundland and Labrador");regions.add("Northwest Territories");
    regions.add("Nova Scotia");regions.add("Nunavut");regions.add("Ontario");regions.add("Prince Edward Island");regions.add("Quebec");regions.add("Saskatchewan");
    regions.add("Yukon Territory");
    model.put("regions", regions);
  }
  
  @Autowired
  public void setAmazonS3Client(AmazonS3Client amazonS3Client)
  {
    this.amazonS3Client = amazonS3Client;
  }
}
