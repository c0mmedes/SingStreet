import React, { useEffect, useState, useRef } from "react";
import "../../css/work/Music.css";
import { Link } from "react-router-dom";
import { api } from "../../services/httpService";

const music = () => {
  return (
    <div>
      <form className="entSearchWrap">
        <input
          placeholder="노래합작 검색. . ."
          type="text"
          className="entSearchInput"></input>
        <input type="submit" value="검색" className="entSearchSubmit"></input>
        <select>
          <option value="allEnt">최신순</option>
          <option value="myEnt">인기순</option>
        </select>
      </form>
      <div class="cards-list">
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white music">
            <p>내꺼하자</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>{" "}
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>{" "}
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 3">
          <div class="card_image">
            <img src="https://media.giphy.com/media/10SvWCbt1ytWCc/giphy.gif" />
          </div>
          <div class="card_title">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 4">
          <div class="card_image">
            <img src="https://media.giphy.com/media/LwIyvaNcnzsD6/giphy.gif" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 1">
          <div class="card_image">
            <img src="https://i.redd.it/b3esnz5ra34y.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
        <div class="musicCard 2">
          <div class="card_image">
            <img src="https://cdn.blackmilkclothing.com/media/wysiwyg/Wallpapers/PhoneWallpapers_FloralCoral.jpg" />
          </div>
          <div class="card_title title-white">
            <p>Card Title</p>
          </div>
        </div>
      </div>
      <div className="MusicCreatebtnWrap">
        <Link to="/musiccreate">
          <button className="musicCreatebtn">노래합작 올리기!</button>
        </Link>
      </div>
    </div>
  );
};

export default music;
