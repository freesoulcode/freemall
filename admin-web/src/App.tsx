import { useState } from "react";
import LoginPage from "./pages/Login";
import { Button } from "@/components/ui/button";
import { logoutApi } from "./api/auth";
import { useTranslation } from "react-i18next";
import LanguageSwitcher from "./components/LanguageSwitcher";

function App() {
  const { t } = useTranslation();
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
      <div className="absolute top-4 right-4">
        <LanguageSwitcher />
      </div>
      <div className="text-2xl font-bold">
        {t('app.welcome', { username: username || '' })}
      </div>
      <p className="text-gray-600">{t('app.successMessage')}</p>
      <Button onClick={handleLogout} variant="outline">
        {t('app.logout')}
      </Button>
    </div>
  );
}

export default App;