import React, { useEffect, useState } from 'react';
import { Switch, Route, Link } from 'react-router-dom';
import Admin from './pages/Admin';
import Feed from './pages/Feed';
import Home from './pages/Home';
import './App.css';
import './pages/css/Home.css'
import './pages/css/Admin.css'
import axios from 'axios';

const websocket = new WebSocket('ws://localhost:1234/ws');

// React components
function App() {

//state variables
const [title, setTitle] = useState('');
const [price, setPrice] = useState('');
const [type, setType] = useState('');
const [Listings, setListings] = useState([]);

const fetchListings = async () => {
  const results =  await axios.get(`/viewListings`);

  setListings([results.data]);
};

useEffect(() => {
  fetchListings();
}, []);

const submitHandler = async (e) => {
  await axios.post(`/addListings`, {
    title,
    price,
    type,
  });
  fetchListings();


}
  return (
    <div className="App">
      <h1>The Peeps</h1>
      <nav className="topnav">
        <ul>
          <li>
            <Link to="/">Home</Link>
          </li>
          <li>
            <Link to="/feed">Feed</Link>
          </li>
        </ul>
      </nav>
      <fieldset>
        
            <form onSubmit={submitHandler}>
              <div>
              <input
              type = 'text'
              placeholder='Title'
              value = {title}
              onChange={(e) => setTitle(e.target.value)}
              />
              </div>
              <div>
              <input
              type = 'number'
              placeholder='Price'
              value = {price}
              onChange={(e) => setPrice(e.target.value)}
              />
              </div>
              <div>
              <input
              type = 'text'
              placeholder='Type'
              value = {type}
              onChange={(e) => setType(e.target.value)}
              />
              <div>
              <button type='submit' id='submit'>Add Item</button>
              </div>
              </div>
            </form>
        </fieldset>
      <Switch>
        <Route path="/admin">
          <Admin />
        </Route>
        <Route path="/feed">
          <Feed />
        </Route>
        <Route path="/">
          <Home />
        </Route>
      </Switch>
    </div>
  );
}
export default App;