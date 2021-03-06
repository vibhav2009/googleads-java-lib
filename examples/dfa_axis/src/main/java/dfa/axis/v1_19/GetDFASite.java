// Copyright 2012 Google Inc. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package dfa.axis.v1_19;

import com.google.api.ads.common.lib.auth.OfflineCredentials;
import com.google.api.ads.common.lib.auth.OfflineCredentials.Api;
import com.google.api.ads.dfa.axis.factory.DfaServices;
import com.google.api.ads.dfa.axis.v1_19.DfaSite;
import com.google.api.ads.dfa.axis.v1_19.DfaSiteRecordSet;
import com.google.api.ads.dfa.axis.v1_19.DfaSiteSearchCriteria;
import com.google.api.ads.dfa.axis.v1_19.SiteRemote;
import com.google.api.ads.dfa.lib.client.DfaSession;
import com.google.api.client.auth.oauth2.Credential;

/**
 * This example gets existing DFA sites based on a given search criteria.
 * Results are limited to the first 10.
 *
 * Credentials and properties in {@code fromFile()} are pulled from the
 * "ads.properties" file. See README for more info.
 *
 * Tags: site.getDfaSites
 *
 * @author Joseph DiLallo
 */
public class GetDFASite {

  // Specify the search criteria.
  private static final String SEARCH_STRING = "INSERT_SEARCH_CRITERIA_HERE";

  public static void runExample(
      DfaServices dfaServices, DfaSession session, String searchString) throws Exception {
    // Request the service.
    SiteRemote service = dfaServices.get(session, SiteRemote.class);

    // Create DFA site search criteria structure.
    DfaSiteSearchCriteria searchCriteria = new DfaSiteSearchCriteria();
    searchCriteria.setPageSize(10);
    searchCriteria.setSearchString(searchString);

    // Get the sites.
    DfaSiteRecordSet dfaSiteRecordSet = service.getDfaSites(searchCriteria);

    // Display DFA site names and IDs.
    if (dfaSiteRecordSet.getRecords() != null) {
      for (DfaSite result : dfaSiteRecordSet.getRecords()) {
        System.out.println("DFA site with name \"" + result.getName()
            + "\" and ID \"" + result.getId() + "\" was found.");
      }
    } else {
      System.out.print("No DFA sites found for your search criteria.");
    }
  }

  public static void main(String[] args) throws Exception {
    // Generate a refreshable OAuth2 credential, which replaces legacy passwords
    // and can be used in place of a service account.
    Credential oAuth2Credential = new OfflineCredentials.Builder()
        .forApi(Api.DFA)
        .fromFile()
        .build()
        .generateCredential();

    // Construct a DfaSession.
    DfaSession session = new DfaSession.Builder()
        .fromFile()
        .withOAuth2Credential(oAuth2Credential)
        .build();

    DfaServices dfaServices = new DfaServices();

    runExample(dfaServices, session, SEARCH_STRING);
  }
}
