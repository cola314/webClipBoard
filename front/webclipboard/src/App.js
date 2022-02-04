import "./App.css";
import { useEffect, useState } from "react";
import ApplicationTab from "./Component/ApplicationTab";
import TextSharePage from "./Page/TestSharePage";
import SettingPage from "./Page/SettingPage";
import FileSharePage from "./Page/FileSharePage";
import FileListPage from "./Page/FileListPage";

const App = () => {
  const [currentTabIndex, setCurrentTabIndex] = useState(0);
  const [username, setUsername] = useState(
    () => window.localStorage.getItem("username") || ""
  );

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
              텍스트 공유
            </ApplicationTab>
            <ApplicationTab
              currentTabIndex={currentTabIndex}
              tabIndex={1}
              setCurrentTabIndex={setCurrentTabIndex}
            >
              파일 공유
            </ApplicationTab>
            <ApplicationTab
              currentTabIndex={currentTabIndex}
              tabIndex={2}
              setCurrentTabIndex={setCurrentTabIndex}
            >
              최근 기록
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
        <TextSharePage isVisible={currentTabIndex === 0} username={username} />
        <FileSharePage isVisible={currentTabIndex === 1} username={username} />
        {currentTabIndex === 2 && (
          <FileListPage isVisible={true} username={username} />
        )}
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
