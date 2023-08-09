<Route path="/entmain/:entId/:entMasterId/:entName/*" element={<EntMain />}>
  {/* 중첩된 라우트 설정 */}
  <Route
    path=""
    element={
      // isLogin 상태에 따라 컴포넌트를 선택
      isLogin ? <EntFeed /> : <Navigate to="/login" />
    }
  />
  <Route
    path="entapply"
    element={
      // isLogin 상태에 따라 컴포넌트를 선택
      isLogin ? <EntApplyContainer /> : <Navigate to="/login" />
    }
  />
  <Route
    path="entapplicants"
    element={
      // isLogin 상태에 따라 컴포넌트를 선택
      isLogin ? <EntApplicantsContainer /> : <Navigate to="/login" />
    }
  />
</Route>;
