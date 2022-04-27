import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import dto.ListingDto;
import org.bson.Document;

public class MongoConnection {
    MongoCollection<Document> mongoCollection;

    public MongoConnection() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase database = mongoClient.getDatabase("MyDatabase");
        mongoCollection = database.getCollection("Listings");
    }
    // Handler for adding Listing
    public String addListing (ListingDto listing) {
        Document newListing = new Document()
                .append("Title", listing.getTitle())
                .append("Price", listing.getPrice())
                .append("Type", listing.getType());
        mongoCollection.insertOne(newListing);
        return "Listing Added to Database";
    }
    //getting all the listing
    public Document getListing (ListingDto listing) {
        Document newListing = new Document()
                .append("_id",listing.getId())
                .append("Title", listing.getTitle())
                .append("Price", listing.getPrice())
                .append("Type", listing.getType());
        FindIterable<Document> listings = mongoCollection.find();
        for (Document matchingListing : listings) {
            if(matchingListing.getObjectId("_id").equals(listing.getId()))
            {
                return matchingListing;
            }
        }
        return null;
    }
    //view a specific listing
    public List<Object> getListings() {
        List<Object> list = new ArrayList<>();
        FindIterable<Document> listings = mongoCollection.find();
        for (Document listing : listings) {
            list.add(listing);
        }
        return list;
    }
//couldnt get this work
    public ListingDto editListing (ListingDto user) {
        return null;
    }
    //couldn't get this work
    public void deleteListing (String id) {
        Document query = new Document("_id", id);
        mongoCollection.deleteOne( query);

    }
}