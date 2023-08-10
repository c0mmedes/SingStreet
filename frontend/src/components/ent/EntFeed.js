//src/components/ent/EntMain.js
import React, { useEffect, useState } from "react";
import "../../css/ent/EntMain.css";
import { Link, Outlet, useParams } from "react-router-dom";
import { api } from "../../services/httpService";

const EntFeed = () => {
  return (
    <>
      <div className="profile">
        <h1>헤더(엔터명)</h1>
        <div className="videos">
          <div className="video">
            <div className="video-time">15.13</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/368244254.sd.mp4?s=2dc98d46cc5c55913b309928d1d14769f76bc6f9&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">Planning Helps Make</div>
            <div className="view">15.4k views</div>
          </div>
          <div className="video">
            <div className="video-time">13.10</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/356200184.sd.mp4?s=f528556cafba1d369984dc341104e7eef8bb71bb&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">This Is Cloaud Break</div>
            <div className="view">13.2k views</div>
          </div>
          <div className="video">
            <div className="video-time">15.30</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/364324653.sd.mp4?s=7ded2b451ac7f5dfaf1375277aa0308cdf5d011c&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">Lost Your Mind</div>
            <div className="view">11.7k views</div>
          </div>
          <div className="video">
            <div className="video-time">11.30</div>
            <video muted>
              <source
                src="https://player.vimeo.com/external/399004885.sd.mp4?s=1d39443bef9856dacc4d3ba2c6be0881e38b7e66&profile_id=139&oauth2_token_id=57447761"
                type="video/mp4"
              />
            </video>
            <div className="video-content">Planning Helps Make</div>
            <div className="view">9.2k views</div>
          </div>
        </div>
      </div>

      <div className="trends">
        <h1>피드영역 </h1>
      </div>
    </>
  );
};

export default EntFeed;
