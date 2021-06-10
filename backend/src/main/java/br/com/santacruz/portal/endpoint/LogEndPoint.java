package br.com.santacruz.portal.endpoint;

import br.com.santacruz.portal.fachada.LogDetailService;
import br.com.santacruz.portal.fachada.LogService;
import br.com.santacruz.portal.modelo.Log;
import br.com.santacruz.portal.modelo.dto.LogDTO;
import br.com.santacruz.portal.modelo.dto.LogDetailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Path("logs")
public class LogEndPoint {

    @Autowired
    private LogService service;

    @Autowired
    private LogDetailService logDetailService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogsWithFilter(@RequestBody LogDTO dto){
        List<Log> list = service.getLogsWithFilter(dto);
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/details/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLogDetailsByLogId(@PathParam("id") long id) {
        List<LogDetailDTO> list = logDetailService.getLogDetailsByLogId(id).stream().map(logD -> LogDetailDTO.builder()
                .description(logD.getDescription())
                .information(logD.getInformation())
                .dateOccured(logD.getDateOccured())
                .status(logD.getStatus()).build()).collect(Collectors.toList());
        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

}
