import { FC, PropsWithChildren } from "react";
import { queryClient } from "@/lib/react-query";
import { QueryClientProvider } from "@tanstack/react-query";
import React from "react";
import { ErrorBoundary } from "react-error-boundary";
import { HelmetProvider } from "react-helmet-async";
import { createTheme, MantineProvider } from "@mantine/core";

import "@mantine/core/styles.css";
import "@mantine/notifications/styles.css";
import { Notifications } from "@mantine/notifications";

const theme = createTheme({
  defaultRadius: "xs",
  primaryColor: "indigo",
});

export const MainProvider: FC<PropsWithChildren> = ({ children }) => {
  return (
    <React.Suspense fallback={<div>Loading...</div>}>
      <ErrorBoundary FallbackComponent={() => <div>Error occured</div>}>
        <HelmetProvider>
          <QueryClientProvider client={queryClient}>
            <MantineProvider theme={theme}>
              <Notifications />
              {children}
            </MantineProvider>
          </QueryClientProvider>
        </HelmetProvider>
      </ErrorBoundary>
    </React.Suspense>
  );
};
