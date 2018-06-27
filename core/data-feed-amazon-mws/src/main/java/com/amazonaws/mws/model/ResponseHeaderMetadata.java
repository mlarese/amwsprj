/******************************************************************************* 
 *  Copyright 2008-2012 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License"); 
 *  
 *  You may not use this file except in compliance with the License. 
 *  You may obtain a copy of the License at: http://aws.amazon.com/apache2.0
 *  This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 *  CONDITIONS OF ANY KIND, either express or implied. See the License for the 
 *  specific language governing permissions and limitations under the License.
 * ***************************************************************************** 
 * 
 *  Marketplace Web Service Java Library
 *  API Version: 2009-01-01
 * 
 */



package com.amazonaws.mws.model;

import java.util.Date;
import java.util.List;

public class ResponseHeaderMetadata {
  private String requestId;
  private String responseContext;
  private String timestamp;
  /** The max quota allowed for a quota period */
  private Double quotaMax;

  /** Quota remaining within this quota period */
  private Double quotaRemaining;

  /** Time that this quota period ends */
  private Date quotaResetsAt;

  public ResponseHeaderMetadata() {}

  public ResponseHeaderMetadata(String requestId, String responseContext, String timestamp, Double quotaMax, Double quotaRemaining, Date quotaResetsAt) {
    this.requestId = requestId;
    this.responseContext = responseContext;
    this.timestamp = timestamp;
    this.quotaMax = quotaMax;
    this.quotaRemaining = quotaRemaining;
    this.quotaResetsAt = quotaResetsAt;
  }

  public String getRequestId() {
    return requestId;
  }

  public String getResponseContext() {
    return responseContext;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public Double getQuotaMax() { return quotaMax;}

  public Double getQuotaRemaining() {return  quotaRemaining;}

  public Date getQuotaResetsAt() {return quotaResetsAt;}

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("requestId : " + requestId + "\n");
    sb.append("responseContext : " + responseContext + "\n");
    sb.append("timestamp : " + timestamp + "\n");
    sb.append("quotaMax : " + quotaMax+ "\n");
    sb.append("quotaRemaining : " + quotaRemaining+ "\n");
    sb.append("quotaResetsAt : " + quotaResetsAt+ "\n");
    return sb.toString();
  }
}
