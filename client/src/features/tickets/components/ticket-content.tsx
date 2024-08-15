import { Stack, Text, Title } from "@mantine/core";
import { FC } from "react";

interface TicketContentProps {
  title: string;
  description: string;
}

export const TicketContent: FC<TicketContentProps> = ({
  title,
  description,
}) => {
  return (
    <Stack>
      <Title order={2}>{title}</Title>
      <Text>{description}</Text>
    </Stack>
  );
};
