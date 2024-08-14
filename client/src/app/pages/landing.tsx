import { Anchor, Group } from "@mantine/core";
import { FC } from "react";

export const LandingPage: FC = () => {
  return (
    <Group>
      <Anchor href="/users/registration">Register</Anchor>
      <Anchor href="/users/login">Log in</Anchor>
    </Group>
  );
};
