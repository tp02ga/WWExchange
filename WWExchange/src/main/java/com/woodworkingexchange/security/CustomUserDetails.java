package com.woodworkingexchange.security;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;

import com.woodworkingexchange.domain.Authorities;
import com.woodworkingexchange.domain.Users;

public class CustomUserDetails extends Users implements UserDetails
{
  private static final long serialVersionUID = -2052691227087262541L;

  public CustomUserDetails(Users user)
  {
    super(user);
  }

  @Override
  public Set<Authorities> getAuthorities()
  {
    return super.getAuthorities();
  }
  
  @Override
  public String getUsername()
  {
    return super.getEmail();
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }

}
