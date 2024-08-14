import { AuthLayout } from "@/components/layouts/auth-layout";
import { LoginCard } from "@/features/users/components/login-card";
import { Anchor, Container, Title, Text } from "@mantine/core";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

export const LoginPage: FC = () => {
  const navigate = useNavigate();

  const handleLoginSuccess = () => {
    navigate("/");
  };

  return (
    <AuthLayout title="Login">
      <Container w={420}>
        <Title ta="center">Welcome back!</Title>
        <Text mb={30} c="dimmed" size="sm" ta="center" mt={5}>
          Do not have an account yet?{" "}
          <Anchor href="/users/registration" size="sm">
            Create account
          </Anchor>
        </Text>
        <LoginCard onSuccess={handleLoginSuccess} />
      </Container>
    </AuthLayout>
  );
};
