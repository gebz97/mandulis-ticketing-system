import { AuthLayout } from "@/components/layouts/auth-layout";
import { RegistrationCompleteCard } from "@/features/users/components/registration-complete-card";
import { Container, Title, Text } from "@mantine/core";
import { FC } from "react";
import { useSearchParams } from "react-router-dom";

export const RegistrationCompletePage: FC = () => {
  const [searchParams] = useSearchParams();

  const firstName = searchParams.get("firstName");
  const lastName = searchParams.get("lastName");
  const email = searchParams.get("email");

  if (!email || !firstName || !lastName) {
    return <div>One of the parameters is undefined</div>;
  }

  return (
    <AuthLayout title="Registration Complete">
      <Container w={420}>
        <Title ta="center"> Complete registration</Title>
        <Text mb={30} c="dimmed" size="sm" ta="center" mt={5}>
          You are almost there!
        </Text>
        <Title ta="center"></Title>
        <RegistrationCompleteCard
          email={email}
          firstName={firstName}
          lastName={lastName}
        />
      </Container>
    </AuthLayout>
  );
};
