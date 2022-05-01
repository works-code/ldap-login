# ldap-login
--
사용 가이드
1. ldap.properties : ldap 연결 정보 설정
2. 사용자가 속한 그룹명은 그룹의 ou값을 가져오는데 수정하고 싶다면 'CustomLdapUserMapper' 클래스의 controls, attributes 변수 부분 참고 하여 수정
3. 어플리케이션 기동한뒤 http://localhost:8080 접속하여 로그인 테스트
4. 로그인 완료시 화면에 'Hello World /성,이름 표기/ !! [groups] /그룹 리스트 표기/' 문구 노출