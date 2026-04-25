import { useTranslation } from "react-i18next";
import {
  Package,
  ShoppingCart,
  Users,
  TrendingUp,
} from "lucide-react";

const DashboardPage: React.FC = () => {
  const { t } = useTranslation();

  const stats = [
    { label: t("dashboard.totalProducts"), value: "--", icon: Package },
    { label: t("dashboard.totalOrders"), value: "--", icon: ShoppingCart },
    { label: t("dashboard.totalCustomers"), value: "--", icon: Users },
    { label: t("dashboard.todayRevenue"), value: "--", icon: TrendingUp },
  ];

  return (
    <div className="space-y-6">
      <div>
        <h2 className="text-2xl font-bold tracking-tight">{t("dashboard.title")}</h2>
        <p className="text-muted-foreground">{t("dashboard.overview")}</p>
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <div key={stat.label} className="rounded-xl border bg-card text-card-foreground shadow p-6">
            <div className="flex items-center justify-between">
              <span className="text-sm font-medium text-muted-foreground">{stat.label}</span>
              <stat.icon className="h-4 w-4 text-muted-foreground" />
            </div>
            <div className="mt-2 text-2xl font-bold">{stat.value}</div>
          </div>
        ))}
      </div>

      <div className="grid gap-4 md:grid-cols-2">
        <div className="rounded-xl border bg-card text-card-foreground shadow p-6">
          <h3 className="font-semibold mb-2">{t("dashboard.recentOrders")}</h3>
          <p className="text-sm text-muted-foreground">{t("dashboard.noData")}</p>
        </div>
        <div className="rounded-xl border bg-card text-card-foreground shadow p-6">
          <h3 className="font-semibold mb-2">{t("dashboard.hotProducts")}</h3>
          <p className="text-sm text-muted-foreground">{t("dashboard.noData")}</p>
        </div>
      </div>
    </div>
  );
};

export default DashboardPage;