import { AuthLayout } from "@/components/layouts/auth-layout";
import {
  useRegisterUser,
  RegisterUserInput,
} from "@/features/users/api/register-user";
import { RegistrationForm } from "@/features/users/components/registration-form";
import { RegisterUserResponse } from "@/features/users/models/register-user-response";
import { Anchor, Container, Title, Text, Card } from "@mantine/core";
import { notifications } from "@mantine/notifications";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

export const RegistrationPage: FC = () => {
  const navigate = useNavigate();

  const register = useRegisterUser();

  const handleRegistrationSuccess = (data: RegisterUserResponse) => {
    navigate(
      `/user/registration/complete?email=${data.email}&firstName=${data.firstName}&lastName=${data.lastName}`
    );
  };

  const handleSubmit = (data: RegisterUserInput) => {
    register.mutate(
      { data: data },
      {
        onSuccess: handleRegistrationSuccess,
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
    <AuthLayout title="Registration">
      <Container w={420}>
        <Title ta="center">Let's create my account!</Title>
        <Text mb={30} c="dimmed" size="sm" ta="center" mt={5}>
          Already have an account?{" "}
          <Anchor href="/users/login" size="sm">
            Log in
          </Anchor>
        </Text>
        <Card withBorder p={30} mt="md">
          <RegistrationForm
            onSubmit={handleSubmit}
            isLoading={register.isPending}
          />
        </Card>
      </Container>
    </AuthLayout>
  );
};
