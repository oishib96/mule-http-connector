/*
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.test.http.functional.requester;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mule.functional.junit4.matchers.MessageMatchers.hasPayload;
import static org.mule.tck.processor.FlowAssert.verify;
import static org.mule.test.http.AllureConstants.HttpFeature.HTTP_EXTENSION;
import static org.mule.test.http.AllureConstants.HttpFeature.HttpStory.MULTIPART;
import org.mule.runtime.core.api.InternalEvent;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.test.http.functional.AbstractHttpTestCase;

import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

@Feature(HTTP_EXTENSION)
@Story(MULTIPART)
@Ignore("MULE-12985: Move multipart test cases to HTTP service and adapt extension ones")
@Issue("MULE-12985")
public class HttpRequestPartsTypeTestCase extends AbstractHttpTestCase {

  @Rule
  public DynamicPort listenPort = new DynamicPort("port");

  private byte[] dataBytes = "{ \'I am a JSON attachment!\' }".getBytes(UTF_8);

  @Override
  protected String getConfigFile() {
    return "http-request-attachment-config.xml";
  }

  /**
   * "Unsupported content type" means one that is not out of the box supported by javax.activation.
   */
  @Test
  public void inputStreamAttachmentWithUnsupportedContentType() throws Exception {
    //    MultiPartPayload partPayload = getMultiPartPayload(dataBytes);
    final InternalEvent result = flowRunner("attachmentFromBytes").withPayload("").run();
    assertThat(result.getMessage(), hasPayload(equalTo("OK")));
    verify("reqWithAttachment");
  }

  /**
   * "Unsupported content type" means one that is not out of the box supported by javax.activation.
   */
  @Test
  public void byteArrayAttachmentWithUnsupportedContentType() throws Exception {
    //    MultiPartPayload partPayload = getMultiPartPayload(new ByteArrayInputStream(dataBytes));
    final InternalEvent result = flowRunner("attachmentFromStream").withPayload("").run();
    assertThat(result.getMessage(), hasPayload(equalTo("OK")));
    verify("reqWithAttachment");
  }

  //  private MultiPartPayload getMultiPartPayload(Object data) {
  //    PartAttributes partAttributes = new PartAttributes("someJson");
  //    Message part = builder().value(data).attributesValue(partAttributes).mediaType(JSON).build();
  //    return new DefaultMultiPartPayload(part);
  //  }
}