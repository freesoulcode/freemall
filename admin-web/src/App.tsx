import { useState } from "react";
import LoginPage from "./pages/Login";
import { Button } from "@/components/ui/button";
import { logoutApi } from "./api/auth";

function App() {
  const [token, setToken] = useState<string | null>(localStorage.getItem("token"));
  const [username, setUsername] = useState<string | null>(localStorage.getItem("username"));

  const handleLoginSuccess = (newToken: string, newUsername: string) => {
    localStorage.setItem("token", newToken);
    localStorage.setItem("username", newUsername);
    setToken(newToken);
    setUsername(newUsername);
  };

  const handleLogout = async () => {
    try {
      await logoutApi();
    } finally {
      localStorage.removeItem("token");
      localStorage.removeItem("username");
      setToken(null);
      setUsername(null);
    }
  };

  if (!token) {
    return <LoginPage onLoginSuccess={handleLoginSuccess} />;
  }

  return (
    <div className="flex min-h-svh flex-col items-center justify-center space-y-4">
      <div className="text-2xl font-bold">欢迎回来, {username}!</div>
      <p className="text-gray-600">您已成功登录 FreeMall 管理后台。</p>
      <Button onClick={handleLogout} variant="outline">退出登录</Button>
    </div>
  );
}

export default App;