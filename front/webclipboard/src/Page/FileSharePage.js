import { useState } from "react";
import * as fileService from "../Service/fileService";

const FileSharePage = ({ isVisible, username }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const onSubmit = e => {
    e.preventDefault();
    if (selectedFile == null) return;

    const formData = new FormData();
    formData.append("multiPartFile", selectedFile);
    fileService
      .uploadFile(username, formData)
      .then(res => {
        alert("업로드가 완료됬습니다");
        console.log(`파일 업로드 완료 ${res.data}`);
        setSelectedFile(null);
      })
      .catch(err => {
        alert(`업로드에 실패했습니다\n${err}`);
        console.error(err);
      });
  };
  const handleFileSelect = e => {
    setSelectedFile(e.target.files[0]);
  };
  return (
    <form className={isVisible ? "" : "is-hidden"} onSubmit={onSubmit}>
      <div className="field">
        <label className="label">이름</label>
        <div className="control">
          <input
            type="text"
            className="input"
            value={username}
            placeholder="설정에서 이름을 변경해주세요"
            disabled
          />
        </div>
      </div>
      <div className="field">
        <label className="label">파일</label>
        <div className="file has-name is-fullwidth">
          <label className="file-label">
            <input
              className="file-input"
              type="file"
              onChange={handleFileSelect}
            />
            <span className="file-cta">
              <span className="file-icon">
                <i className="fas fa-upload"></i>
              </span>
              <span className="file-label">파일 선택</span>
            </span>
            <span className="file-name">{selectedFile?.name}</span>
          </label>
        </div>
      </div>
      <div className="field is-grouped is-grouped-right">
        <div className="control">
          <button className="button is-info">등록</button>
        </div>
      </div>
    </form>
  );
};

export default FileSharePage;
