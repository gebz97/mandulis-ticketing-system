import { FC, useMemo } from "react";
import { MainProvider } from "./main-provider";
import { useQueryClient } from "@tanstack/react-query";
import { RouterProvider } from "react-router-dom";
import { createRouter } from "./pages";

const AppRouter = () => {
  const queryClient = useQueryClient();

  const router = useMemo(() => createRouter(queryClient), [queryClient]);

  return <RouterProvider router={router} />;
};

export const App: FC = () => {
  return (
    <MainProvider>
      <AppRouter />
    </MainProvider>
  );
};
