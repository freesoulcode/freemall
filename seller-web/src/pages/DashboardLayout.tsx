import { Outlet } from "react-router-dom";
import { SidebarInset, SidebarProvider, SidebarTrigger } from "@/components/ui/sidebar";
import AppSidebar from "@/components/AppSidebar";
import { Separator } from "@/components/ui/separator";
import {
  Breadcrumb,
  BreadcrumbItem,
  BreadcrumbLink,
  BreadcrumbList,
  BreadcrumbPage,
  BreadcrumbSeparator,
} from "@/components/ui/breadcrumb";
import { useTranslation } from "react-i18next";
import { useLocation } from "react-router-dom";

const breadcrumbMap: Record<string, string> = {};

interface BreadcrumbItemData {
  label: string;
  path: string;
  isLast: boolean;
}

function DashboardLayout({ username, onLogout }: {
  username: string | null;
  onLogout: () => void;
}) {
  const { t } = useTranslation();
  const location = useLocation();

  // Build breadcrumb from path
  const pathSegments = location.pathname.split("/").filter(Boolean);
  const breadcrumbItems: BreadcrumbItemData[] = pathSegments.length === 0
    ? [{ label: t("menu.dashboard"), path: "/", isLast: true }]
    : pathSegments.map((segment, index) => ({
        label: breadcrumbMap[`/${pathSegments.slice(0, index + 1).join("/")}`] || t(`menu.${segment}`) || segment,
        path: `/${pathSegments.slice(0, index + 1).join("/")}`,
        isLast: index === pathSegments.length - 1,
      }));

  return (
    <SidebarProvider>
      <AppSidebar username={username} onLogout={onLogout} />
      <SidebarInset>
        <header className="flex h-14 shrink-0 items-center gap-2 border-b px-4">
          <SidebarTrigger className="-ml-1" />
          <Separator orientation="vertical" className="mr-2 h-4" />
          <Breadcrumb>
            <BreadcrumbList>
              {breadcrumbItems.map((item, index) => (
                <BreadcrumbItem key={item.path}>
                  {index > 0 && <BreadcrumbSeparator />}
                  {item.isLast ? (
                    <BreadcrumbPage>{item.label}</BreadcrumbPage>
                  ) : (
                    <BreadcrumbLink href={item.path}>{item.label}</BreadcrumbLink>
                  )}
                </BreadcrumbItem>
              ))}
            </BreadcrumbList>
          </Breadcrumb>
        </header>
        <div className="flex flex-1 flex-col gap-4 p-4">
          <Outlet />
        </div>
      </SidebarInset>
    </SidebarProvider>
  );
}

export default DashboardLayout;