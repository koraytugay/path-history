package biz.tugay.pathHistory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Named
@Singleton
@Path("/")
public class PathHistoryResource {

  PathHistoryJob pathHistoryJob;

  @Inject
  public PathHistoryResource(PathHistoryJob pathHistoryJob) {
    this.pathHistoryJob = pathHistoryJob;
  }

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String stopJob(@QueryParam("action") String action) {
    switch (action) {
      case "stop":
        pathHistoryJob.stopJob();
        return "Stopped!";
      case "start":
        pathHistoryJob.startJob();
        return "Started!";
    }
    throw new BadRequestException("Nope!");
  }
}
