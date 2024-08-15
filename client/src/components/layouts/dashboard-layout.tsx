import { FC, PropsWithChildren } from "react";
import { Head } from "../seo/head";
import { AppShell, Burger, Group, NavLink, Title } from "@mantine/core";
import { useDisclosure } from "@mantine/hooks";
import { SITE_NAME, SITE_NAVIGATION } from "@/config/site";
import { useLocation } from "react-router-dom";

interface DashboardLayoutProps {
  title: string;
  description?: string;
}

export const DashboardLayout: FC<PropsWithChildren<DashboardLayoutProps>> = ({
  children,
  title,
  description = "",
}) => {
  const [opened, { toggle }] = useDisclosure();
  const location = useLocation();

  return (
    <>
      <Head title={title} description={description} />
      <AppShell
        header={{ height: 60 }}
        navbar={{
          width: 300,
          breakpoint: "sm",
          collapsed: { mobile: !opened },
        }}
        padding="md"
      >
        <AppShell.Header>
          <Group h="100%" px="md">
            <Burger
              opened={opened}
              onClick={toggle}
              hiddenFrom="sm"
              size="sm"
            />
            <Title order={2}>{SITE_NAME}</Title>
          </Group>
        </AppShell.Header>
        <AppShell.Navbar>
          {SITE_NAVIGATION.map((nav) => (
            <NavLink
              key={nav.name}
              href={nav.href}
              label={nav.name}
              active={nav.href === location.pathname}
              leftSection={<nav.icon />}
              defaultOpened={location.pathname.startsWith(nav.href)}
            >
              {nav.children?.map((subnav) => (
                <NavLink
                  key={subnav.name}
                  href={subnav.href}
                  label={subnav.name}
                  active={location.pathname === subnav.href}
                  leftSection={<subnav.icon />}
                />
              ))}
            </NavLink>
          ))}
        </AppShell.Navbar>
        <AppShell.Main bg="gray.0">{children}</AppShell.Main>
      </AppShell>
    </>
  );
};
