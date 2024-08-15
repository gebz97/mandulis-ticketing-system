import { AuthLayout } from "@/components/layouts/auth-layout";
import { LoginForm } from "@/features/users/components/login-form";
import { Anchor, Container, Title, Text, Card } from "@mantine/core";
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
          <Anchor href="/user/registration" size="sm">
            Create account
          </Anchor>
        </Text>
        <Card withBorder p={30}>
          <LoginForm onSuccess={handleLoginSuccess} />
        </Card>
      </Container>
    </AuthLayout>
  );
};
