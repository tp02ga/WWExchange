package com.woodworkingexchange.web;

public enum Condition
{
  EXCELLENT(5, "Excellent"),
  VERY_GOOD(4, "Very Good"),
  GOOD(3, "Good"),
  FAIR(2, "Fair"),
  AS_IS(1, "As Is");
  
  private int condition;
  private String conditionString;
  
  Condition(int condition, String conditionString)
  {
    this.condition = condition;
    this.conditionString = conditionString;
  }

  public String getConditionString()
  {
    return conditionString;
  }
  
  public int getCondition()
  {
    return condition;
  }
}
