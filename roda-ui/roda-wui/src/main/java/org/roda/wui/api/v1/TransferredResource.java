/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */
package org.roda.wui.api.v1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
<<<<<<< HEAD
import javax.ws.rs.QueryParam;
=======
import javax.ws.rs.PathParam;
>>>>>>> WIP: Transferred resource REST API
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.roda.core.RodaCoreFactory;
import org.roda.core.common.UserUtility;
import org.roda.core.data.common.RODAException;
import org.roda.core.data.v2.RodaUser;
import org.roda.wui.api.controllers.Browser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@Path(TransferredResource.ENDPOINT)
@Api(value = TransferredResource.SWAGGER_ENDPOINT)
public class TransferredResource {
  public static final String ENDPOINT = "/v1/transferred";
  public static final String SWAGGER_ENDPOINT = "v1 transferred";

  @Context
  private HttpServletRequest request;

  @POST
  //@Path("/new/{path: [a-zA-Z0-9_/]+}")
  @Path("/new")
  public Response uploadFiles(@ApiParam(value = "The id of the parent", required = true) @PathParam("parentId") String parentId, @FormDataParam("upl") InputStream inputStream,
    @FormDataParam("upl") FormDataContentDisposition fileDetail) throws RODAException {
    // get user
    RodaUser user = UserUtility.getApiUser(request, RodaCoreFactory.getIndexService());
    // delegate action to controller
    Browser.createTransferredResourceFile(user,parentId,fileDetail.getFileName(), inputStream);
    
    // FIXME give a better answer
    return Response.ok().entity("{'status':'success'}").build();
  }

}
