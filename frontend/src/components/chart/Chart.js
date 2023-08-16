import React from "react";
import Footer from "../layout/Footer";
import "../../css/chart/Chart.css";
import sampleuni from "../../assets/sampleuni.png";
import chart1 from "../../assets/chart1.PNG";
import chart2 from "../../assets/chart2.PNG";
import chart3 from "../../assets/chart3.PNG";
import chart4 from "../../assets/chart4.PNG";
const chart = ({ userInfo }) => {
  return (
    <div className="chartContainer">
      <div className="chartCard">
        <div className="chartTitle ">
          <h1>月刊차트</h1>
          <p>
            The world liked a lot of music this month.
            <br />
            Discover the ranking.
          </p>
        </div>

        <div className="chartBox chartBox1">
          <div className="chartNumber chartNumber1">01</div>
          <div className="chartCover chartCover1">
            <img src={sampleuni} alt="01" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName chartName1">
            <span> 아야츠노유니 - 내꺼 하는 법</span>
            <p>준혁엔터</p>
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=wnJ6LuUFpMo">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox chartBox2">
          {/* <!-- #02 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">02</div>
          {/* <!-- Cover --> */}
          <div className="chartCover chartCover2">
            <img src={chart1} alt="02" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>king krule - The Ozz</span>
            <p>하늘 엔터테인먼트</p>
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=ozv4q2ov3Mk">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox chartBox3">
          {/* <!-- #03 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">03</div>
          {/* <!-- Cover --> */}
          <div className="chartCover chartCover3">
            <img src={chart2} alt="03" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>걸스데이 - 좋은날</span>
            <p>걸걸오란씨</p>
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox chartBox4">
          {/* <!-- #04 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">04</div>
          {/* <!-- Cover --> */}
          <div className="chartCover chartCover4">
            <img src={chart3} alt="04" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>MC Noriaki - Unstoppable</span>
            <p>닥터리 엔터테인먼트</p>
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox">
          {/* <!-- #05 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">05</div>
          {/* <!-- Cover --> */}
          <div className="chartCover chartCover5">
            <img src={chart4} alt="05" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>최성 - Blue snail</span>
            <p>WXY 엔터테인먼트</p>
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox">
          {/* <!-- #06 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">06</div>
          {/* <!-- Cover --> */}
          <div className="chartCover">
            <img src="http://bit.ly/2vlRum1" alt="03" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>Attention</span> Charlie Puth
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox">
          {/* <!-- #07 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">07</div>
          {/* <!-- Cover --> */}
          <div className="chartCover">
            <img src="http://bit.ly/2vlRum1" alt="03" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>Attention</span> Charlie Puth
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox">
          {/* <!-- #08 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">08</div>
          {/* <!-- Cover --> */}
          <div className="chartCover">
            <img src="http://bit.ly/2vlRum1" alt="03" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>Attention</span> Charlie Puth
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox">
          {/* <!-- #09 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">09</div>
          {/* <!-- Cover --> */}
          <div className="chartCover">
            <img src="http://bit.ly/2vlRum1" alt="03" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>Attention</span> Charlie Puth
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>

        {/* <!-- Separator --> */}
        <div className="separator"></div>

        <div className="chartBox">
          {/* <!-- #10 --> */}
          {/* <!-- Number--> */}
          <div className="chartNumber">10</div>
          {/* <!-- Cover --> */}
          <div className="chartCover">
            <img src="http://bit.ly/2vlRum1" alt="03" />
          </div>
          {/* <!-- Name --> */}
          <div className="chartName">
            <span>Attention</span> Charlie Puth
          </div>
          {/* <!-- Button --> */}
          <div className="link">
            <a href="https://www.youtube.com/watch?v=nfs8NYg7yQM">Listen</a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default chart;
