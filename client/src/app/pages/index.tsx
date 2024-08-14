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
      path: "/users/registration",
      lazy: async () => {
        const { RegistrationPage } = await import("./users/registration");
        return { Component: RegistrationPage };
      },
    },
    {
      path: "/users/registration/complete",
      lazy: async () => {
        const { RegistrationCompletePage } = await import(
          "./users/registration/complete"
        );
        return { Component: RegistrationCompletePage };
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
