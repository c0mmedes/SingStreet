//src/components/ent/EntMain.js
import React, { useEffect, useState } from "react";
import "../../css/ent/EntMain.css";
import { Link, Outlet, useParams } from "react-router-dom";
import { api } from "../../services/httpService";

const EntFeed = () => {
  return (
    <>
      <div className="profile">
        <h1>프로필영역</h1>
      </div>

      <div className="trends">
        <a href="#">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 56 50"
            fill="currentColor">
            <path
              d="M5.03 12h-5v38h56V12h-5zm31.999 20.262l-12.951 7.521a2.02 2.02 0 01-2.04.004 1.984 1.984 0 01-1.008-1.735V23.01c0-.724.377-1.372 1.008-1.735a2.047 2.047 0 012.04.003L37.029 28.8a1.983 1.983 0 011.001 1.731c0 .719-.374 1.366-1.001 1.731z"
              data-original="#000000"
              className="active-path"
            />
            <path
              d="M23.03 38.051v.004l12.994-7.524-12.951-7.525zM12.03 0h32v4h-32zM50.03 6h-44v4h44z"
              data-original="#000000"
              className="active-path"
            />
          </svg>
          See what's trending
        </a>
        <div className="follow-buttons">
          <button className="follow">Date Added</button>
          <button className="follow follow-option active">Most Popular</button>
        </div>
      </div>
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
        <div className="video">
          <div className="video-time">6.35</div>
          <video muted>
            <source
              src="https://player.vimeo.com/external/353556576.sd.mp4?s=8e942d8680fe908418143e63e04b8798982d5bea&profile_id=139&oauth2_token_id=57447761"
              type="video/mp4"
            />
          </video>
          <div className="video-content">Research In Advertising</div>
          <div className="view">18.4k views</div>
        </div>
        <div className="video">
          <div className="video-time">2.21</div>
          <video muted>
            <source
              src="https://player.vimeo.com/external/368556609.sd.mp4?s=3fa896a1d6d8c04382a9b8f33053d2eaabe4342b&profile_id=139&oauth2_token_id=57447761"
              type="video/mp4"
            />
          </video>
          <div className="video-content">See The Unmatched</div>
          <div className="view">3.4k views</div>
        </div>
        <div className="video">
          <div className="video-time">12.10</div>
          <video muted>
            <source
              src="https://player.vimeo.com/external/285489976.sd.mp4?s=4fa6461f93f18e0d6cfc30461fffb05311d60a28&profile_id=164&oauth2_token_id=57447761"
              type="video/mp4"
            />
          </video>
          <div className="video-content">Dance In The Air</div>
          <div className="view">17.4k views</div>
        </div>
        <div className="video">
          <div className="video-time">7.50</div>
          <video muted>
            <source
              src="https://player.vimeo.com/external/325725646.sd.mp4?s=763c0f293299052689f8344b3a155ea2b4a1c92b&profile_id=164&oauth2_token_id=57447761"
              type="video/mp4"
            />
          </video>
          <div className="video-content">Sadness Will Last Forever</div>
          <div className="view">12.6k views</div>
        </div>
      </div>
    </>
  );
};

export default EntFeed;
