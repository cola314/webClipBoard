const ApplicationTab = ({
  children,
  currentTabIndex,
  tabIndex,
  setCurrentTabIndex,
}) => {
  return (
    <li
      className={tabIndex === currentTabIndex ? "is-active" : ""}
      onClick={() => setCurrentTabIndex(tabIndex)}
    >
      <a>{children}</a>
    </li>
  );
};

export default ApplicationTab;
