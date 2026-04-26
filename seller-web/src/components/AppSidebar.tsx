import { Link, useLocation } from "react-router-dom";
import { useTranslation } from "react-i18next";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarRail,
} from "@/components/ui/sidebar";
import {
  ShoppingCart,
  LogOut,
  ChevronRight,
  LayoutDashboard,
  Box,
  ArrowUpDown,
  Tags,
  AlertTriangle,
  BarChart3,
  FileText,
  RefreshCcw,
  Truck,
  Receipt,
  Paintbrush,
  TrendingUp,
  Users,
  Ticket,
  Megaphone,
  Share2,
  Banknote,
  Scale,
  Shield,
  Puzzle,
} from "lucide-react";
import { Collapsible, CollapsibleContent, CollapsibleTrigger } from "@/components/ui/collapsible";

// Menu structure based on the mindmap
const menuStructure = [
  {
    groupKey: "menu.productManagement",
    items: [
      { key: "menu.productPublish", path: "/product/publish", icon: Box },
      { key: "menu.productStatus", path: "/product/status", icon: ArrowUpDown },
      { key: "menu.categoryBrand", path: "/product/category", icon: Tags },
      { key: "menu.inventoryAlert", path: "/product/inventory", icon: AlertTriangle },
      { key: "menu.productAnalysis", path: "/product/analysis", icon: BarChart3 },
    ],
  },
  {
    groupKey: "menu.transactionOrder",
    items: [
      { key: "menu.orderProcessing", path: "/order/list", icon: FileText },
      { key: "menu.afterSales", path: "/order/aftersales", icon: RefreshCcw },
      { key: "menu.logistics", path: "/order/logistics", icon: Truck },
      { key: "menu.invoice", path: "/order/invoice", icon: Receipt },
    ],
  },
  {
    groupKey: "menu.storeData",
    items: [
      { key: "menu.storeDecoration", path: "/store/decoration", icon: Paintbrush },
      { key: "menu.dataDashboard", path: "/store/dashboard", icon: TrendingUp },
      { key: "menu.customerManagement", path: "/store/customer", icon: Users },
    ],
  },
  {
    groupKey: "menu.marketingPromotion",
    items: [
      { key: "menu.promotion", path: "/marketing/promotion", icon: Ticket },
      { key: "menu.advertising", path: "/marketing/ads", icon: Megaphone },
      { key: "menu.contentDistribution", path: "/marketing/content", icon: Share2 },
    ],
  },
  {
    groupKey: "menu.financeSystem",
    items: [
      { key: "menu.settlement", path: "/finance/settlement", icon: Banknote },
      { key: "menu.costProfit", path: "/finance/cost", icon: Scale },
      { key: "menu.accountPermission", path: "/system/account", icon: Shield },
      { key: "menu.appService", path: "/system/apps", icon: Puzzle },
    ],
  },
];

function AppSidebar({ onLogout }: { username: string | null; onLogout: () => void }) {
  const { t } = useTranslation();
  const location = useLocation();

  return (
    <Sidebar collapsible="icon">
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton size="lg" asChild>
              <Link to="/">
                <div className="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
                  <ShoppingCart className="size-4" />
                </div>
                <div className="flex flex-col gap-0.5 leading-none">
                  <span className="font-semibold">{t('menu.brand')}</span>
                  <span className="text-xs text-sidebar-foreground/70">{t('menu.subtitle')}</span>
                </div>
              </Link>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>

      <SidebarContent>
        {/* Dashboard link */}
        <SidebarGroup>
          <SidebarGroupContent>
            <SidebarMenu>
              <SidebarMenuItem>
                <SidebarMenuButton
                  asChild
                  isActive={location.pathname === "/"}
                  tooltip={t("menu.dashboard")}
                >
                  <Link to="/">
                    <LayoutDashboard />
                    <span>{t("menu.dashboard")}</span>
                  </Link>
                </SidebarMenuButton>
              </SidebarMenuItem>
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>

        {/* Menu groups with collapsible sub-items */}
        {menuStructure.map((group) => (
          <Collapsible key={group.groupKey} defaultOpen asChild>
            <SidebarGroup>
              <SidebarGroupLabel asChild>
                <CollapsibleTrigger>
                  <span>{t(group.groupKey)}</span>
                  <ChevronRight className="ml-auto transition-transform group-data-[state=open]:rotate-90" />
                </CollapsibleTrigger>
              </SidebarGroupLabel>
              <CollapsibleContent>
                <SidebarGroupContent>
                  <SidebarMenu>
                    {group.items.map((item) => (
                      <SidebarMenuItem key={item.key}>
                        <SidebarMenuButton
                          asChild
                          isActive={location.pathname === item.path}
                          tooltip={t(item.key)}
                        >
                          <Link to={item.path}>
                            <item.icon />
                            <span>{t(item.key)}</span>
                          </Link>
                        </SidebarMenuButton>
                      </SidebarMenuItem>
                    ))}
                  </SidebarMenu>
                </SidebarGroupContent>
              </CollapsibleContent>
            </SidebarGroup>
          </Collapsible>
        ))}
      </SidebarContent>

      <SidebarFooter>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton asChild tooltip={t("menu.logout")}>
              <button onClick={onLogout} className="w-full">
                <LogOut className="size-4" />
                <span>{t("menu.logout")}</span>
              </button>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
      <SidebarRail />
    </Sidebar>
  );
}

export default AppSidebar;