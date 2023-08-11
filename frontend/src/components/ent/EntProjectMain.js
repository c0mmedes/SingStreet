import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, Link } from "react-router-dom";

const EntProjectMain = () => {
    const { entId, entMasterId, entName, projectId } = useParams();

    return (
        <div>
            <Link to={`/entmain/${entId}/${entMasterId}/${entName}/entproject/${projectId}/studio`}>
                <div>스튜디오 입장</div>
            </Link>
        </div>
    );
};

export default EntProjectMain;