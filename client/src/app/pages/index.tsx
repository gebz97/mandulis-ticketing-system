import { QueryClient } from "@tanstack/react-query";
import { createBrowserRouter } from "react-router-dom";

export const createRouter = (queryClient: QueryClient) =>
  createBrowserRouter([
    // landing
    {
      path: "/",
      lazy: async () => {
        const { LandingPage } = await import("./landing");
        return { Component: LandingPage };
      },
    },

    // users
    {
      path: "/users/login",
      lazy: async () => {
        const { LoginPage } = await import("./users/login");
        return { Component: LoginPage };
      },
    },
    {
      path: "/users/register",
      lazy: async () => {
        const { RegisterPage } = await import("./users/register");
        return { Component: RegisterPage };
      },
    },

    // fallback
    {
      path: "*",
      lazy: async () => {
        const { NotFoundPage } = await import("./not-found");
        return { Component: NotFoundPage };
      },
    },
  ]);
