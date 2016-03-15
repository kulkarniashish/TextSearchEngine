package com.intelliment.service;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.intelliment.model.SearchText;

@Path("/")
public interface SearchService {

	@POST
	@Path("search")
	@Produces(MediaType.APPLICATION_JSON)	
	Object search(SearchText searchText);

	@GET
	@Path("top/{N}")
	@Produces({"text/csv"})
	Response topN(@PathParam("N") Integer N) throws IOException;

}
