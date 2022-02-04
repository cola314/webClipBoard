import { useEffect, useState } from "react";

const SettingPage = ({ username, setUsername, isVisible }) => {
  const [inputUsername, setInputUsername] = useState("");

  useEffect(() => {
    setInputUsername(username);
  }, [username]);

  return (
    <div className={`tabPage ${isVisible ? "" : "is-hidden"}`}>
      <div className="field">
        <label className="label">이름</label>
        <div className="control">
          <input
            type="text"
            className="input"
            value={inputUsername}
            onChange={e => setInputUsername(e.target.value)}
            placeholder="이름을 입력해주세요"
          />
        </div>
      </div>

      <div className="field is-grouped is-grouped-right">
        <div className="control">
          <button
            className="button is-info"
            onClick={() => setUsername(inputUsername)}
          >
            확인
          </button>
        </div>
      </div>
    </div>
  );
};

export default SettingPage;
