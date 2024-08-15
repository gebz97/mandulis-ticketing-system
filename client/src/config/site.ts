import { NAVIGATION_LINK } from "@/types/api";
import { IconBox } from "@tabler/icons-react";

export const SITE_NAME = "Mandulis";

export const SITE_NAVIGATION: NAVIGATION_LINK[] = [
  {
    name: "Home",
    href: "/dashboard",
    icon: IconBox,
  },
  {
    name: "Tickets",
    href: "/tickets",
    icon: IconBox,
    children: [
      {
        name: "Create",
        href: "/tickets/create",
        icon: IconBox,
      },
      {
        name: "Filter",
        href: "/tickets/filter",
        icon: IconBox,
      },
      {
        name: "Categories",
        href: "/tickets/categories",
        icon: IconBox,
      },
      {
        name: "Tags",
        href: "/tickets/tags",
        icon: IconBox,
      },
    ],
  },
  {
    name: "Reports",
    href: "/reports",
    icon: IconBox,
  },
  {
    name: "User management",
    href: "/users",
    icon: IconBox,
  },
  {
    name: "Group management",
    href: "/groups",
    icon: IconBox,
  },
  {
    name: "Permissions",
    href: "/permissions",
    icon: IconBox,
  },
];
