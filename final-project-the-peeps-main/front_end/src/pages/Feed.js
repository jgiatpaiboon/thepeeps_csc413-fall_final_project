import React, { useEffect, useState } from 'react';
import axios from 'axios';
import '../pages/css/Feed.css'


function Feed() {

  const [Listings, setListings] = useState([]);
  const [title, setTitle] = useState('');
  const [price, setPrice] = useState('');
  const [type, setType] = useState('');

  const fetchListings = async () => {
    const results =  await axios.get(`/viewListings`);
  
    setListings([results.data]);
  };
  
  useEffect(() => {
    fetchListings();
  }, []);
  
const ViewHandler = async (e) => {
  await axios.put(`/viewListings/_id`, {
  title,
  price,
  type,
  });
}

  const EditHandler = async (e) => {
    await axios.put(`/editListing`, {
     title,
     price,
     type,
   });
  }

  const DeleteHandler = async (e) => {
    await axios.delete(`/deleteListing`, {
      title,
      price,
      type,
    });
   }
   
  var list = Listings.map(function(listing){
    return(
      <div>
        <h1>Listings</h1> 
        {listing.data.map(item => 
            <div class="card">
              <div id="imgTitle">Title: {item.Title}</div>
              <div id="listingPrice">Price: {item.Price}</div>
              <div id="imgDescription">Type: {item.Type}</div>
              
              <form onSubmit={ViewHandler}><button type='submit' id='view'>View Listing</button></form>
              <form onSubmit={EditHandler}><button type='submit' id='edit'>Edit Listing</button></form>
              <form onSubmit={DeleteHandler}><button type='submit' id='view'>Delete Listing</button></form>
              
            </div>
        )}
      </div>
    )
  })
  return (
    <div>
      <h2>Feed Page</h2>
      <div className="feed">{list}</div>
    </div>
  );
}

export default Feed;