import { AuthLayout } from "@/components/layouts/auth-layout";
import { RegistrationCard } from "@/features/users/components/registration-card";
import { RegisterUserResponseModel } from "@/features/users/dtos/register-user-response";
import { Anchor, Container, Title, Text } from "@mantine/core";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

export const RegistrationPage: FC = () => {
  const navigate = useNavigate();

  const handleRegistrationSuccess = (data: RegisterUserResponseModel) => {
    navigate(
      `/users/registration/complete?email=${data.email}&firstName=${data.firstName}&lastName=${data.lastName}`
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
        <RegistrationCard onSuccess={handleRegistrationSuccess} />
      </Container>
    </AuthLayout>
  );
};
