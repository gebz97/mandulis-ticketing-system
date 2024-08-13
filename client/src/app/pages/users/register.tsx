import { AuthLayout } from "@/components/layouts/auth-layout";
import { LoginCard } from "@/features/users/components/login-card";
import { RegisterCard } from "@/features/users/components/register-card";
import { Anchor, Container, Title, Text } from "@mantine/core";
import { FC } from "react";

export const RegisterPage: FC = () => {
  return (
    <AuthLayout title="Registration">
      <Container w={420}>
        <Title ta="center">Let's create my account!</Title>
        <Text mb={30} c="dimmed" size="sm" ta="center" mt={5}>
          Already have an account?{" "}
          <Anchor href="/users/login" size="sm">
            Log in
          </Anchor>
        </Text>
        <RegisterCard />
      </Container>
    </AuthLayout>
  );
};
