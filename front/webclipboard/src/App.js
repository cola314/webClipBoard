import "./App.css";
import { useEffect, useState } from "react";
import ApplicationTab from "./Component/ApplicationTab";
import TextSharePage from "./Page/TestSharePage";
import SettingPage from "./Page/SettingPage";
import FileSharePage from "./Page/FileSharePage";
import FileListPage from "./Page/FileListPage";

const App = () => {
  let isFirstLoad = true;
  const [currentTabIndex, setCurrentTabIndex] = useState(0);
  const [username, setUsername] = useState(
    () => window.localStorage.getItem("username") || ""
  );

  const moveSettingTabIfUsernameIsEmpty = () => {
    if (isFirstLoad && username.trim() === "") {
      isFirstLoad = false;
      setCurrentTabIndex(3);
    }
  };

  useEffect(() => {
    moveSettingTabIfUsernameIsEmpty();
  });

  useEffect(() => {
    window.localStorage.setItem("username", username);
  }, [username]);

  return (
    <section className="section">
      <div className="container">
        <div className="tabs is-boxed" id="mainTabs">
          <ul>
            <ApplicationTab
              currentTabIndex={currentTabIndex}
              tabIndex={0}
              setCurrentTabIndex={setCurrentTabIndex}
            >
              최근 기록
            </ApplicationTab>
            <ApplicationTab
              currentTabIndex={currentTabIndex}
              tabIndex={1}
              setCurrentTabIndex={setCurrentTabIndex}
            >
              텍스트 공유
            </ApplicationTab>
            <ApplicationTab
              currentTabIndex={currentTabIndex}
              tabIndex={2}
              setCurrentTabIndex={setCurrentTabIndex}
            >
              파일 공유
            </ApplicationTab>
            <ApplicationTab
              currentTabIndex={currentTabIndex}
              tabIndex={3}
              setCurrentTabIndex={setCurrentTabIndex}
            >
              설정
            </ApplicationTab>
          </ul>
        </div>
        {currentTabIndex === 0 && (
          <FileListPage isVisible={true} username={username} />
        )}
        <TextSharePage isVisible={currentTabIndex === 1} username={username} />
        <FileSharePage isVisible={currentTabIndex === 2} username={username} />
        <SettingPage
          isVisible={currentTabIndex === 3}
          username={username}
          setUsername={setUsername}
        />
      </div>
    </section>
  );
};

export default App;
