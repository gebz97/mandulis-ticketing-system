import { AuthLayout } from "@/components/layouts/auth-layout";
import { useLoginUser, LoginUserInput } from "@/features/users/api/login-user";
import { LoginForm } from "@/features/users/components/login-form";
import {
  Anchor,
  Container,
  Title,
  Text,
  Card,
  Notification,
} from "@mantine/core";
import { notifications } from "@mantine/notifications";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

export const LoginPage: FC = () => {
  const navigate = useNavigate();
  const login = useLoginUser();

  const handleLoginSuccess = () => {
    navigate("/");
  };

  const handleSubmit = (data: LoginUserInput) => {
    login.mutate(
      { data: data },
      {
        onSuccess: handleLoginSuccess,
        onError: (e) => {
          notifications.show({
            color: "red",
            position: "top-center",
            title: "Create comment finished with error",
            withBorder: true,
            message: e.response?.data.message || e.message,
          });
        },
      }
    );
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
        <Card withBorder p={30} mt="md">
          <LoginForm onSubmit={handleSubmit} isLoading={login.isPending} />
        </Card>
      </Container>
    </AuthLayout>
  );
};
