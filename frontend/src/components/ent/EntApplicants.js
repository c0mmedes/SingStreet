import React,{useState} from 'react';
import { useParams } from 'react-router-dom';
import {api} from "../../services/httpService"

const EntApplicants = ({myEntList}) => {
    // axios 인스턴스
    const apiInstance = api();
    // 라우터 파라미터에서 가져올 entId 변수
    const {entId} = useParams();
    // 상태관리
    const [applicantList, setApplicantList] = useState([]);
    

    const getEntApplicantList = async () => {
        try{
            const accessToken = await sessionStorage.getItem("accessToken");
            const res = await apiInstance.get(`ent/apply/${entId}`,{
                headers: {
                    Authorization: `Bearer ${accessToken}`, // Bearer 토큰 포함
                  },
            });
            /*
            res.data
            [
                {
                  "applId": 0,
                  "createAt": "2023-08-07T07:34:01.357Z",
                  "nickname": "string",
                  "userId": 0
                }
            ] 
            */
            setApplicantList(res.data);
        } catch{}
    } ;

    return (
        <div>
            
        </div>
    );
};

export default EntApplicants;