import { useEffect, useState } from "react";
import { copyTextToClipboard } from "../Util/clipboard";
import * as fileService from "../Service/fileService";

const TextItemPanel = ({ file, refresh }) => {
  const [text, setText] = useState("로드 중...");
  useEffect(() => {
    fileService
      .getTextContent(file.creator, file.id)
      .then(res => {
        setText(res.data);
      })
      .catch(err => {
        setText(`텍스트 로드 실패\n${err}`);
        console.error(err);
      });
  }, [file]);
  const onCopyClicked = () => {
    copyTextToClipboard(text)
      .then(_ => {
        alert("클립보드에 복사되었습니다");
      })
      .catch(err => {
        alert(`복사에 실패했습니다\n${err}`);
      });
  };
  const onDeleteClick = () => {
    fileService
      .deleteFile(file.creator, file.id)
      .then(res => {
        alert("텍스트 삭제 완료");
        console.log(`텍스트 삭제 완료 ${file.id}`);
        refresh();
      })
      .catch(err => {
        alert(`텍스트 삭제 실패\n${err}`);
        console.error(err);
      });
  };
  return (
    <div className="box">
      <div className="field">
        <label className="label">텍스트</label>
        <div className="control">
          <textarea
            className="textarea"
            placeholder="내용이 없습니다"
            disabled
            value={text}
          ></textarea>
        </div>
      </div>
      <div className="field is-grouped is-grouped-right">
        <div className="control">
          <button className="button is-danger" onClick={onDeleteClick}>
            삭제
          </button>
        </div>
        <div className="control">
          <button className="button is-info" onClick={onCopyClicked}>
            내용 복사
          </button>
        </div>
      </div>
    </div>
  );
};

const FileItemPanel = ({ file, refresh }) => {
  const [fileUrl, setFileUrl] = useState("#");
  useEffect(() => {
    setFileUrl(fileService.getDownloadFileUrl(file.creator, file.id));
  }, [file]);
  const onCopyClicked = () => {
    copyTextToClipboard(fileUrl)
      .then(_ => {
        alert("클립보드에 복사되었습니다");
      })
      .catch(err => {
        alert(`복사에 실패했습니다\n${err}`);
      });
  };
  const onDeleteClick = () => {
    fileService
      .deleteFile(file.creator, file.id)
      .then(res => {
        alert("파일 삭제 완료");
        console.log(`파일 삭제 완료 ${file.id}`);
        refresh();
      })
      .catch(err => {
        alert(`파일 삭제 실패\n${err}`);
        console.error(err);
      });
  };
  return (
    <div className="box">
      <div className="field">
        <label className="label">파일</label>
        <div className="control">
          <a href={fileUrl} target="_blank" rel="noopener noreferrer">
            {file.id}
          </a>
        </div>
      </div>
      <div className="field is-grouped is-grouped-right">
        <div className="control">
          <button className="button is-danger" onClick={onDeleteClick}>
            삭제
          </button>
        </div>
        <div className="control">
          <button className="button is-info" onClick={onCopyClicked}>
            주소 복사
          </button>
        </div>
      </div>
    </div>
  );
};

const FileListPage = ({ isVisible, username }) => {
  const [files, setFiles] = useState([]);

  const refresh = username => {
    if (username.trim() === "") return;

    fileService
      .getFiles(username)
      .then(res => {
        setFiles(res.data);
      })
      .catch(err => {
        alert(`파일 로드 실패\n${err}`);
      });
  };

  useEffect(() => {
    refresh(username);
  }, [username]);

  return (
    <div className={isVisible ? "" : "is-hidden"}>
      <div className="field">
        <label className="label">이름</label>
        <div className="control">
          <input
            type="text"
            className="input"
            placeholder="설정에서 이름을 변경해주세요"
            value={username}
            disabled
          />
        </div>
      </div>
      {files.map(file => {
        return file.type === "text" ? (
          <TextItemPanel
            key={file.id}
            file={file}
            refresh={() => refresh(username)}
          />
        ) : (
          <FileItemPanel
            key={file.id}
            file={file}
            refresh={() => refresh(username)}
          />
        );
      })}
    </div>
  );
};

export default FileListPage;
