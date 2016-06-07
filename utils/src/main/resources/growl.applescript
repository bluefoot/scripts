set messageToShow to "%s"
tell application id "com.Growl.GrowlHelperApp"
  set the allNotificationsList to {"%s"}
  
  set the enabledNotificationsList to {"%s"}
  register as application "%s" all notifications allNotificationsList default notifications enabledNotificationsList icon of application "Script Editor"
  notify with name "%s" %s title "%s" description messageToShow application name "%s" sticky %s
end tell