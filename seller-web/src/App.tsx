import { useState } from "react";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import VerifyPage from "./pages/Verify";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import LanguageSwitcher from "./components/LanguageSwitcher";

type PageState = 'login' | 'register' | 'verify' | 'dashboard';

function App() {
  const { t } = useTranslation();
  const [page, setPage] = useState<PageState>(
    localStorage.getItem("seller_token") ? 'dashboard' : 'login'
  );
  const [token, setToken] = useState<string | null>(localStorage.getItem("seller_token"));
  const [username, setUsername] = useState<string | null>(localStorage.getItem("seller_username"));
  const [pendingMerchantId, setPendingMerchantId] = useState<number | null>(null);

  const handleLoginSuccess = (newToken: string, newUsername: string) => {
    localStorage.setItem("seller_token", newToken);
    localStorage.setItem("seller_username", newUsername);
    setToken(newToken);
    setUsername(newUsername);
    setPage('dashboard');
  };

  const handleRegisterSuccess = (merchantId: number) => {
    setPendingMerchantId(merchantId);
    setPage('verify');
  };

  const handleVerifySuccess = () => {
    setPage('login');
  };

  const handleLogout = () => {
    localStorage.removeItem("seller_token");
    localStorage.removeItem("seller_username");
    setToken(null);
    setUsername(null);
    setPage('login');
  };

  return (
    <div className="min-h-screen relative">
      <div className="absolute top-4 right-4 z-50">
        <LanguageSwitcher />
      </div>

      {page === 'login' && (
        <LoginPage 
          onLoginSuccess={handleLoginSuccess} 
          onGoToRegister={() => setPage('register')} 
        />
      )}
      
      {page === 'register' && (
        <RegisterPage 
          onRegisterSuccess={handleRegisterSuccess} 
          onGoToLogin={() => setPage('login')} 
        />
      )}

      {page === 'verify' && pendingMerchantId && (
        <VerifyPage 
          merchantId={pendingMerchantId} 
          onVerifySuccess={handleVerifySuccess} 
        />
      )}

      {page === 'dashboard' && token && (
        <div className="flex min-h-svh flex-col items-center justify-center space-y-4">
          <div className="text-2xl font-bold">
            {t('dashboard.welcome', { username: username || '' })}
          </div>
          <p className="text-gray-600">{t('dashboard.success')}</p>
          <div className="flex gap-4">
             <Button variant="default">{t('dashboard.manageStore')}</Button>
             <Button onClick={handleLogout} variant="outline">{t('dashboard.logout')}</Button>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
