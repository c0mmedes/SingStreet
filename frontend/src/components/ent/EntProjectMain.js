import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from "react-router-dom";

const EntProjectMain = () => {
    const { entId, entMasterId, entName, projectId } = useParams();

    return (
        <div>
            <Link to={`/entproject/studio/${entId}/${entMasterId}/${entName}/${projectId}`}>
                <div>스튜디오 입장</div>
            </Link>
        </div>
    );
};

export default EntProjectMain;