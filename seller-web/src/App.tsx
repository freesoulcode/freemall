import { useState } from "react";
import { BrowserRouter, Routes, Route, Navigate, useNavigate } from "react-router-dom";
import { TooltipProvider } from "@/components/ui/tooltip";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import VerifyPage from "./pages/Verify";
import QualificationPage from "./pages/Qualification";
import DashboardLayout from "./pages/DashboardLayout";
import DashboardPage from "./pages/Dashboard";
import ProductListPage from "./pages/ProductList";
import ProductFormPage from "./pages/ProductForm";
import PlaceholderPage from "./pages/PlaceholderPage";
import LanguageSwitcher from "./components/LanguageSwitcher";
import {
  ArrowUpDown, Tags, AlertTriangle, BarChart3,
  FileText, RefreshCcw, Truck, Receipt,
  Paintbrush, TrendingUp, Users,
  Ticket, Megaphone, Share2,
  Banknote, Scale, Shield, Puzzle,
} from "lucide-react";

function AuthRoutes({ isLoggedIn, handleLoginSuccess, pendingMerchantId, setPendingMerchantId }: {
  isLoggedIn: boolean;
  handleLoginSuccess: (token: string, username: string, merchantId: string) => void;
  pendingMerchantId: string | null;
  setPendingMerchantId: (id: string | null) => void;
}) {
  const navigate = useNavigate();

  if (isLoggedIn) {
    return <Navigate to="/" />;
  }

  return (
    <Routes>
      <Route path="/login" element={
        <LoginPage
          onLoginSuccess={handleLoginSuccess}
          onGoToRegister={() => navigate('/register')}
        />
      } />
      <Route path="/register" element={
        <RegisterPage
          onRegisterSuccess={(mId) => {
            setPendingMerchantId(mId);
            navigate('/verify');
          }}
          onGoToLogin={() => navigate('/login')}
        />
      } />
      <Route path="/verify" element={
        pendingMerchantId ?
          <VerifyPage merchantId={pendingMerchantId} onVerifySuccess={() => navigate('/qualification')} /> :
          <Navigate to="/login" />
      } />
      <Route path="/qualification" element={
        pendingMerchantId ?
          <QualificationPage merchantId={pendingMerchantId} onSubmitSuccess={() => navigate('/login')} /> :
          <Navigate to="/login" />
      } />
      <Route path="*" element={<Navigate to="/login" />} />
    </Routes>
  );
}

function DashboardRoutes({ username, merchantId, onLogout }: {
  username: string | null;
  merchantId: string | null;
  onLogout: () => void;
}) {
  return (
    <Routes>
      <Route element={<DashboardLayout username={username} merchantId={merchantId} onLogout={onLogout} />}>
        <Route path="/" element={<DashboardPage />} />
        <Route path="/product/status" element={merchantId ? <ProductListPage merchantId={merchantId} /> : null} />
        <Route path="/product/publish" element={merchantId ? <ProductFormPage merchantId={merchantId} /> : null} />

        {/* Placeholder pages */}
        <Route path="/product/category" element={<PlaceholderPage titleKey="menu.categoryBrand" descriptionKey="placeholder.categoryBrand" icon={Tags} />} />
        <Route path="/product/inventory" element={<PlaceholderPage titleKey="menu.inventoryAlert" descriptionKey="placeholder.inventoryAlert" icon={AlertTriangle} />} />
        <Route path="/product/analysis" element={<PlaceholderPage titleKey="menu.productAnalysis" descriptionKey="placeholder.productAnalysis" icon={BarChart3} />} />

        <Route path="/order/list" element={<PlaceholderPage titleKey="menu.orderProcessing" descriptionKey="placeholder.orderProcessing" icon={FileText} />} />
        <Route path="/order/aftersales" element={<PlaceholderPage titleKey="menu.afterSales" descriptionKey="placeholder.afterSales" icon={RefreshCcw} />} />
        <Route path="/order/logistics" element={<PlaceholderPage titleKey="menu.logistics" descriptionKey="placeholder.logistics" icon={Truck} />} />
        <Route path="/order/invoice" element={<PlaceholderPage titleKey="menu.invoice" descriptionKey="placeholder.invoice" icon={Receipt} />} />

        <Route path="/store/decoration" element={<PlaceholderPage titleKey="menu.storeDecoration" descriptionKey="placeholder.storeDecoration" icon={Paintbrush} />} />
        <Route path="/store/dashboard" element={<PlaceholderPage titleKey="menu.dataDashboard" descriptionKey="placeholder.dataDashboard" icon={TrendingUp} />} />
        <Route path="/store/customer" element={<PlaceholderPage titleKey="menu.customerManagement" descriptionKey="placeholder.customerManagement" icon={Users} />} />

        <Route path="/marketing/promotion" element={<PlaceholderPage titleKey="menu.promotion" descriptionKey="placeholder.promotion" icon={Ticket} />} />
        <Route path="/marketing/ads" element={<PlaceholderPage titleKey="menu.advertising" descriptionKey="placeholder.advertising" icon={Megaphone} />} />
        <Route path="/marketing/content" element={<PlaceholderPage titleKey="menu.contentDistribution" descriptionKey="placeholder.contentDistribution" icon={Share2} />} />

        <Route path="/finance/settlement" element={<PlaceholderPage titleKey="menu.settlement" descriptionKey="placeholder.settlement" icon={Banknote} />} />
        <Route path="/finance/cost" element={<PlaceholderPage titleKey="menu.costProfit" descriptionKey="placeholder.costProfit" icon={Scale} />} />
        <Route path="/system/account" element={<PlaceholderPage titleKey="menu.accountPermission" descriptionKey="placeholder.accountPermission" icon={Shield} />} />
        <Route path="/system/apps" element={<PlaceholderPage titleKey="menu.appService" descriptionKey="placeholder.appService" icon={Puzzle} />} />
      </Route>
    </Routes>
  );
}

function App() {
  const [token, setToken] = useState<string | null>(localStorage.getItem("seller_token"));
  const [username, setUsername] = useState<string | null>(localStorage.getItem("seller_username"));
  const [merchantId, setMerchantId] = useState<string | null>(localStorage.getItem("seller_merchant_id"));
  const [pendingMerchantId, setPendingMerchantId] = useState<string | null>(null);

  const handleLoginSuccess = (newToken: string, newUsername: string, newMerchantId: string) => {
    localStorage.setItem("seller_token", newToken);
    localStorage.setItem("seller_username", newUsername);
    localStorage.setItem("seller_merchant_id", newMerchantId);
    setToken(newToken);
    setUsername(newUsername);
    setMerchantId(newMerchantId);
  };

  const handleLogout = () => {
    localStorage.removeItem("seller_token");
    localStorage.removeItem("seller_username");
    localStorage.removeItem("seller_merchant_id");
    setToken(null);
    setUsername(null);
    setMerchantId(null);
  };

  const isLoggedIn = !!token && !!merchantId;

  return (
    <TooltipProvider>
      <BrowserRouter>
        <div className="min-h-screen relative">
          {!isLoggedIn && (
            <div className="absolute top-4 right-4 z-50">
              <LanguageSwitcher />
            </div>
          )}

          {isLoggedIn ? (
            <DashboardRoutes username={username} merchantId={merchantId} onLogout={handleLogout} />
          ) : (
            <AuthRoutes
              isLoggedIn={isLoggedIn}
              handleLoginSuccess={handleLoginSuccess}
              pendingMerchantId={pendingMerchantId}
              setPendingMerchantId={setPendingMerchantId}
            />
          )}
        </div>
      </BrowserRouter>
    </TooltipProvider>
  );
}

export default App;