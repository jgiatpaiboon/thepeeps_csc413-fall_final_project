import com.google.gson.Gson;
import dto.ListingDto;
import dto.ResponseDto;
import dto.StatusResponse;

import static spark.Spark.*;

public class RestServer {
    public static void main(String[] args){
        port(1234);
        webSocket("/ws", WebSocketHandler.class);

        MongoConnection listingService = new MongoConnection();


        // Handler for adding Listing
        post("/addListings", (request, response) -> {
            response.type("application/json");
            ListingDto listing = new Gson().fromJson(request.body(), ListingDto.class);
            listingService.addListing(listing);

            return new Gson()
                    .toJson(new ResponseDto(StatusResponse.SUCCESS));
        });

        // Handler for viewing all Listings
        get("/viewListings", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
                    new ResponseDto(StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(listingService.getListings())));
        });

        // Handler for viewing specific Listing
        get("/viewListings/_id", (request, response) -> {
            response.type("application/json");
            ListingDto listing = new Gson().fromJson(request.body(), ListingDto.class);
            return new Gson().toJson(
                    new ResponseDto(StatusResponse.SUCCESS,new Gson()
                            .toJsonTree(listingService.getListing(listing))));
        });

        // Handler Editing Existing Listings
        put("/editListing/:id", (request, response) -> {
            response.type("application/json");
            ListingDto toEdit = new Gson().fromJson(request.body(), ListingDto.class);
            ListingDto editedListing = listingService.editListing(toEdit);

            if (editedListing != null) {
                return new Gson().toJson(
                        new ResponseDto(StatusResponse.SUCCESS,new Gson()
                                .toJsonTree(editedListing)));
            } else {
                return new Gson().toJson(
                        new ResponseDto(StatusResponse.ERROR,new Gson()
                                .toJson("Error trying to edit listing")));
            }
        });

        // Handler for Deleting Existing Listings
        delete("/deleteListing", (request, response) -> {
            response.type("application/json");
            listingService.deleteListing(request.params("_id"));
            return new Gson().toJson(
                    new ResponseDto(StatusResponse.SUCCESS, "Listing deleted"));
        });
    }
}