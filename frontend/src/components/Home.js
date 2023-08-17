import React from "react";
import { Link } from "react-router-dom";
import "../css/Home.css";
import myImage from "../assets/sample1.png";
import myImage2 from "../assets/chart2.PNG";
import myImage3 from "../assets/chart4.PNG";
import myImage4 from "../assets/chart1.PNG";
import myImage5 from "../assets/chart5.PNG";
import myImage6 from "../assets/chart7.PNG";
import myImage7 from "../assets/chart8.png";
import myImage8 from "../assets/chart3.PNG";

const Home = () => {
  return (
    <div>
      {/* <Link to="/chat">
        <div>상우기의 채팅상담@@@@@@@@@@@@@@@@@@@@@@</div>
      </Link>
      <Link to="/studio">
        <div>studio</div>
      </Link> */}
      <ul className="cards">
        <li>
          <a href="" class="card">
            <img src={myImage} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">B110 엔터</h3>
                  <span class="card__status">
                    # KPOP # 뉴진스 # 10대 # 화이팅
                  </span>
                </div>
              </div>
              <p class="card__description">
                안녕하세요 주로 뉴진스 노래를 커버하는 가상엔터 B110엔터입니다!
                모두 자유롭게
              </p>
            </div>
          </a>
        </li>
        <li>
          <a href="" class="card">
            <img src={myImage2} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">걸걸오란씨 엔터테인먼트</h3>
                  <span class="card__status">#걸걸오란씨 #반모 #09 #10</span>
                </div>
              </div>
              <p class="card__description">
                안녕하세요 주로 아이돌 커버를하는 걸걸오란씨 엔터테인먼트입니다
                ! 여러분의 지원을 기다립니다!
              </p>
            </div>
          </a>
        </li>
        <li>
          <a href="" class="card">
            <img src={myImage3} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">W X Y 엔터테인먼트</h3>
                  <span class="card__status">#WXY #르세라핌 #남자만 #11</span>
                </div>
              </div>
              <p class="card__description">
                르세라핌을 주로 커버하는 XWY 입니다! 남자만 신청해주세요구르트
                ㅋ
              </p>
            </div>
          </a>
        </li>
        <li>
          <a href="" class="card">
            <img src={myImage4} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">핑크오일 엔터테인먼트</h3>
                  <span class="card__status">#틱톡 #핑크오일 #소방차</span>
                </div>
              </div>
              <p class="card__description">
                p i n k o i l ! 안녕하세요 핑크오일입니다 ~ 저희는 주로 8~90년대
                아이돌을 커버합니다.
              </p>
            </div>
          </a>
        </li>
        <li>
          <a href="" class="card">
            <img src={myImage5} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">고흐하학 엔터테인먼트</h3>
                  <span class="card__status">
                    #고흐하학 #고전명곡 #고전파 #98
                  </span>
                </div>
              </div>
              <p class="card__description">
                저희는 고전명작을 재해석하는 그룹입니다.
              </p>
            </div>
          </a>
        </li>
        <li>
          <a href="" class="card">
            <img src={myImage6} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">왕구 엔터테인먼트</h3>
                  <span class="card__status">#왕구 #멍구 #대구 #광구</span>
                </div>
              </div>
              <p class="card__description">
                알락깔락낄릭깔랑알락깔락낄릭깔랑알락깔락낄릭깔랑알락깔락낄릭깔랑알락깔락낄릭깔랑알락깔락낄릭깔랑
              </p>
            </div>
          </a>
        </li>{" "}
        <li>
          <a href="" class="card">
            <img src={myImage7} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">대한굳건 엔터테인먼트</h3>
                  <span class="card__status">
                    #위국헌신군인본분 #군인 #뜨거운사나이 #사나우
                  </span>
                </div>
              </div>
              <p class="card__description">
                충 성 ! 우리는 뜨거운 군인 합작 그룹입니다! 위 국 헌 신 군 인 본
                분
              </p>
            </div>
          </a>
        </li>{" "}
        <li>
          <a href="" class="card">
            <img src={myImage8} class="card__image" alt="" />
            <div class="card__overlay">
              <div class="card__header">
                <div class="card__header-text">
                  <h3 class="card__title">x p x i n x k 엔터</h3>
                  <span class="card__status">#pxixnxk #도망치지마</span>
                </div>
              </div>
              <p class="card__description">
                힘들고 지치더라도 오늘도 화이팅하세요 화이팅 ! 조금밖에안남았어
              </p>
            </div>
          </a>
        </li>
      </ul>
    </div>
  );
};

export default Home;
