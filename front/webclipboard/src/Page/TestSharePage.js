import { useCallback, useState } from "react";
import * as fileService from "../Service/fileService";

const TextSharePage = ({ isVisible, username }) => {
  const [text, setText] = useState("");
  const registerText = async () => {
    fileService
      .createText(username, text)
      .then(res => {
        alert(`텍스트가 등록되었습니다`);
        console.log(`text created ${res.data}`);
        setText("");
      })
      .catch(err => {
        alert(`텍스트 등록에 실패했습니다\n${err}`);
        console.error(err);
      });
  };
  return (
    <div className={isVisible ? "" : "is-hidden"}>
      <div className="field">
        <label className="label">이름</label>
        <div className="control">
          <input
            type="text"
            value={username}
            className="input"
            placeholder="설정에서 이름을 변경해주세요"
            disabled
          />
        </div>
      </div>
      <div className="field">
        <label className="label">내용</label>
        <div className="control">
          <textarea
            className="textarea"
            placeholder="내용을 입력해주세요"
            value={text}
            onChange={e => setText(e.target.value)}
          ></textarea>
        </div>
      </div>
      <div className="field is-grouped is-grouped-right">
        <div className="control">
          <button className="button is-info" onClick={registerText}>
            등록
          </button>
        </div>
      </div>
    </div>
  );
};

export default TextSharePage;
