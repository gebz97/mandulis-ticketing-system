import { AuthLayout } from "@/components/layouts/auth-layout";
import { RegistrationForm } from "@/features/users/components/registration-form";
import { RegisterUserResponseModel } from "@/features/users/models/register-user-response";
import { Anchor, Container, Title, Text, Card } from "@mantine/core";
import { FC } from "react";
import { useNavigate } from "react-router-dom";

export const RegistrationPage: FC = () => {
  const navigate = useNavigate();

  const handleRegistrationSuccess = (data: RegisterUserResponseModel) => {
    navigate(
      `/user/registration/complete?email=${data.email}&firstName=${data.firstName}&lastName=${data.lastName}`
    );
  };

  return (
    <AuthLayout title="Registration">
      <Container w={420}>
        <Title ta="center">Let's create my account!</Title>
        <Text mb={30} c="dimmed" size="sm" ta="center" mt={5}>
          Already have an account?{" "}
          <Anchor href="/user/login" size="sm">
            Log in
          </Anchor>
        </Text>
        <Card withBorder p={30}>
          <RegistrationForm onSuccess={handleRegistrationSuccess} />
        </Card>
      </Container>
    </AuthLayout>
  );
};
