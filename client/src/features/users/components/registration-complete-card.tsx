import { Button, Card, Stack, Text } from "@mantine/core";
import { FC } from "react";

interface RegistrationCompleteCardProps {
  firstName: string;
  lastName: string;
  email: string;
}

export const RegistrationCompleteCard: FC<RegistrationCompleteCardProps> = ({
  email,
  firstName,
  lastName,
}) => {
  return (
    <Card withBorder p={30}>
      <Stack>
        <Text>
          <b>
            {firstName} {lastName}
          </b>
          {", "}
          thank you for choosing our services! To complete registration check
          your email address <b>{email}</b> for verification letter.
        </Text>
        <Button component="a" href="/users/login">
          Log in to my account
        </Button>
      </Stack>
    </Card>
  );
};
