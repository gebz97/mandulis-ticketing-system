import { FC, PropsWithChildren } from "react";
import { Head } from "../seo/head";
import { Center } from "@mantine/core";

interface AuthLayoutProps {
  title: string;
  description?: string;
}

export const AuthLayout: FC<PropsWithChildren<AuthLayoutProps>> = ({
  children,
  title,
  description = "",
}) => {
  return (
    <>
      <Head title={title} description={description} />
      <Center bg="gray.0" style={{ height: "100vh" }}>
        {children}
      </Center>
    </>
  );
};
